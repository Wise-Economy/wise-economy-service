package org.wise_economy.account_aggregator.dto.perfios.registration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class RegistrationPayload {

    private String txnId;
    private String profileId;
    private String userMobileOrHandle;
    private String returnURL; // for deeper UI integration where we need to redirect back to any frontend page url after consent.
}
