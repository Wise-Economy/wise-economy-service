package org.wise_economy.account_aggregator.service;

import org.wise_economy.account_aggregator.dto.perfios.callback.ConsentCallbackResponse;
import org.wise_economy.account_aggregator.dto.registration.RegistrationRequest;
import org.wise_economy.account_aggregator.dto.registration.RegistrationResponse;


public interface RegistrationService {

    RegistrationResponse registerUserAndCreateAccountAtAA(RegistrationRequest registrationRequest);

    Boolean processConsentCallBackFromAA(ConsentCallbackResponse consentCallbackResponse);
}
