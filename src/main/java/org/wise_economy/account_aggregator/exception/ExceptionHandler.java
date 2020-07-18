package org.wise_economy.account_aggregator.exception;

import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.util.Assert;
import org.wise_economy.account_aggregator.utils.ErrorHandler;

@Getter
public class ExceptionHandler extends RuntimeException {


    private static final long serialVersionUID = 6010399527233497864L;

    private int code;

    private String msg;

    private int httpCode;

    private String templateMsg;

    private Object[] args;

    public ExceptionHandler(ErrorHandler we, Object... args) {
        this(null, we, args);
    }

    public ExceptionHandler(Throwable cause, ErrorHandler we, Object... args) {
        super(we.getMessage(args), cause);
        this.code = we.getCode();
        this.msg = this.getMessage();
        this.httpCode = we.getHttpCode();
        this.templateMsg = we.getTemplateMsg();
        this.args = args;
    }

    public ExceptionHandler(@NonNull List<Pair<ErrorHandler, Object[]>> errors) {
        this(null, errors);
    }

    public ExceptionHandler(Throwable cause, @NonNull List<Pair<ErrorHandler, Object[]>> errors) {
        super(getMessageForMultipleErrors(errors), cause);
        this.msg = this.getMessage();
        if (errors.isEmpty()) {
            this.code = ErrorHandler.INTERNAL_SERVER_ERROR.getCode();
            this.httpCode = ErrorHandler.INTERNAL_SERVER_ERROR.getHttpCode();
            this.templateMsg = ErrorHandler.INTERNAL_SERVER_ERROR.getTemplateMsg();
        } else {
            this.code = errors.get(0).getFirst().getCode();
            this.httpCode = errors.get(0).getFirst().getHttpCode();
            this.templateMsg = errors.get(0).getFirst().getTemplateMsg();
        }
    }

    public ExceptionHandler(String msg) {
        this(null, msg);
    }

    public ExceptionHandler(Throwable cause, String msg) {
        super(msg, cause);
    }

    private static String getMessageForMultipleErrors(
            @NonNull List<Pair<ErrorHandler, Object[]>> errors) {
        StringBuilder msgBuilder = new StringBuilder();
        for (Pair<ErrorHandler, Object[]> error : errors) {
            Assert.notNull(error.getFirst(), "Error had a null object");
            Assert.notNull(error.getFirst(), "Error had a null object");
            msgBuilder.append(error.getFirst().getMessage(error.getSecond()));
            msgBuilder.append(";");
        }
        return msgBuilder.toString();
    }

}