package org.wise_economy.account_aggregator.controller;


import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wise_economy.account_aggregator.dto.perfios.callback.ConsentCallbackResponse;
import org.wise_economy.account_aggregator.dto.transactions.DataFetchRequest;
import org.wise_economy.account_aggregator.service.GetDataFromAA;
import org.wise_economy.account_aggregator.service.RegistrationService;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DataFetchController {

    private final GetDataFromAA getDataFromAA;

    private final RegistrationService registrationService;

    @PostMapping("/initiate-data-fetch")
    public ResponseEntity<Boolean> initiateDataFetch(@Valid @RequestBody DataFetchRequest dataFetchRequest) {
        return new ResponseEntity<>(getDataFromAA.getDataFromAA(dataFetchRequest), HttpStatus.OK);
    }

    @PostMapping("/recieve-data-callback")
    public ResponseEntity<Boolean> recieveDataCallBack(@Valid @RequestBody ConsentCallbackResponse consentCallbackResponse) {
        return new ResponseEntity<>(registrationService.processConsentCallBackFromAA(consentCallbackResponse),HttpStatus.OK);
    }

    @PostMapping("/get-transactions")
    public ResponseEntity<Boolean> getBankTransactions(@Valid @RequestBody Long userId) {
        return new ResponseEntity<>(getDataFromAA.getTransactionsFromAA(userId),HttpStatus.OK);
    }
}
