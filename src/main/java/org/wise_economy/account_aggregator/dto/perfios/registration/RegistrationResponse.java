package org.wise_economy.account_aggregator.dto.perfios.registration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class RegistrationResponse {

    private String userId;
    private String txnId;
    private String redirectURL;
    private String consentHandle;
}
