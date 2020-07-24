package org.wise_economy.account_aggregator.dto.perfios.datafetch;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Data
@ToString
public class FetchTransactionsPayload {

    private String txnId;
}
