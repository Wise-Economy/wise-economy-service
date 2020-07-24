package org.wise_economy.account_aggregator.service;

import org.wise_economy.account_aggregator.dto.transactions.DataFetchRequest;

public interface GetDataFromAA {

    Boolean getDataFromAA(DataFetchRequest dataFetchRequest);

    Boolean getTransactionsFromAA(Long userId);
}
