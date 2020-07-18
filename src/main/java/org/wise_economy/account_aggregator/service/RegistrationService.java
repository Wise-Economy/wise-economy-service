package org.wise_economy.account_aggregator.service;

import org.wise_economy.account_aggregator.dto.registration.RegistrationRequest;
import org.wise_economy.account_aggregator.dto.registration.RegistrationResponse;

public interface RegistrationService {

    RegistrationResponse registerUserAndCreateAccountAtPerfios(RegistrationRequest registrationRequest);
}
