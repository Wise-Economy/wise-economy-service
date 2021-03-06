package org.wise_economy.account_aggregator.client.web;

import org.wise_economy.account_aggregator.dto.perfios.datafetch.FetchTransactionsPayload;
import org.wise_economy.account_aggregator.dto.perfios.datafetch.InitiateDataFetch;
import org.wise_economy.account_aggregator.dto.perfios.registration.RegistrationPayload;
import org.wise_economy.account_aggregator.dto.perfios.registration.RegistrationResponse;

public interface AccountAggregator {

    RegistrationResponse initiateRegistrationAndConsent(RegistrationPayload registrationPayload);

    Boolean initiateDataFetch(InitiateDataFetch initiateDataFetch);

    Boolean getTransactions(FetchTransactionsPayload txnId);
}
