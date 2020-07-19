package org.wise_economy.account_aggregator.dto.perfios.callback;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.wise_economy.account_aggregator.domain.ConsentStatus;

@Setter
@Getter
@ToString
@Data
public class ConsentCallbackResponse {

    private String txnId;
    private String userId;
    private ConsentStatus consentStatus;
    private List<Account> accounts;
}
