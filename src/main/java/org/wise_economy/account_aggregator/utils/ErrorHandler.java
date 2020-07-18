package org.wise_economy.account_aggregator.utils;

import java.text.MessageFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum ErrorHandler {
    NO_RESOURCE_FOUND(600, 404,
            "No {0} resource found", "exception.no.resource.found"),
    INVALID_ARGUMENT(601, 400,
            "Invalid argument for {0}", "exception.invalid.argument"),
    INTERNAL_SERVER_ERROR(602, 500,
            "Internal error occurred", "exception.internal.error"),
    BAD_AUTHORIZATION(604, 412,
            "User Principal from request header not matching with passed UserId {0}",
            "exception.internal.auth.bad.authorization"),
    RESOURCE_ACCESS_ERROR(605, 424,
            "{0} Resource Access Error {1}", "exception.resource.access"),
    ILLEGAL_ARGUMENTS(606, 500, "Illegal arguments {0} passed for method {1}",
            "exception.illegal.arguments"),
    NO_MAPPING_FOUND(607, 404,
            "No mapping exists between {0} and {1}", "exception.no.mappings"),
    INTERNAL_CONSUMER_ERROR(608, 0, "Internal error occurred {0}",
            "exception.internal.error"),
    UNSUPPORTED_OPERATION(609, 400, "Unsupported operation {0}",
            "exception.internal.error");

    // The error message returned to the user
    private String msg;

    // The error code within the wise system
    private int code;

    // The http error code that the response will carry
    private int httpCode;

    private String templateMsg;

    ErrorHandler(int code, int httpCode, String msg, String templateMsg, Object... args) {
        this(null, code, httpCode, msg, templateMsg, args);
    }

    ErrorHandler(Throwable cause, int code, int httpCode, String msg, String templateMsg,
             Object... args) {
        MessageFormat fmt = new MessageFormat(msg);
        this.msg = fmt.format(args);
        this.code = code;
        this.httpCode = httpCode;
        this.templateMsg = templateMsg;
    }

    public String getMessage(Object... args) {
        return new MessageFormat(this.msg).format(args);
    }
}