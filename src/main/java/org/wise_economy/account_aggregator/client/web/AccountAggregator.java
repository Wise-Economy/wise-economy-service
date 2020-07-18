package org.wise_economy.account_aggregator.client.web;

import org.wise_economy.account_aggregator.dto.perfios.registration.RegistrationPayload;
import org.wise_economy.account_aggregator.dto.perfios.registration.RegistrationResponse;

public interface AccountAggregator {

    RegistrationResponse initiateRegistrationAndConsent(RegistrationPayload registrationPayload);
}
