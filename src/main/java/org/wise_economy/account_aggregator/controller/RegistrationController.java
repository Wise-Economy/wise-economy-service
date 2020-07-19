package org.wise_economy.account_aggregator.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wise_economy.account_aggregator.dto.perfios.callback.ConsentCallbackResponse;
import org.wise_economy.account_aggregator.dto.registration.RegistrationRequest;
import org.wise_economy.account_aggregator.dto.registration.RegistrationResponse;

import javax.validation.Valid;
import org.wise_economy.account_aggregator.service.RegistrationService;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/initiate-registration")
    public ResponseEntity<RegistrationResponse> initiateRegistration(
        @Valid @RequestBody RegistrationRequest registrationRequest) {
        return new ResponseEntity<>(
            registrationService.registerUserAndCreateAccountAtAA(registrationRequest),
            HttpStatus.OK);
    }

    @PostMapping("/receive-consent-callback")
    public ResponseEntity<Boolean> receieveConsentCallBack(
        @Valid @RequestBody ConsentCallbackResponse consentCallbackResponse) {

        return new ResponseEntity<>(registrationService.processConsentCallBackFromAA(consentCallbackResponse),HttpStatus.OK);
    }
}
