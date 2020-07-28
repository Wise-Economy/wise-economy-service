package org.wise_economy.account_aggregator.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.wise_economy.account_aggregator.client.web.AccountAggregator;
import org.wise_economy.account_aggregator.domain.Account;
import org.wise_economy.account_aggregator.domain.ConsentStatus;
import org.wise_economy.account_aggregator.domain.TransactionType;
import org.wise_economy.account_aggregator.domain.User;
import org.wise_economy.account_aggregator.domain.UserToPerfiosTxnMapping;
import org.wise_economy.account_aggregator.dto.perfios.callback.ConsentCallbackResponse;
import org.wise_economy.account_aggregator.dto.perfios.registration.RegistrationPayload;
import org.wise_economy.account_aggregator.dto.registration.RegistrationRequest;
import org.wise_economy.account_aggregator.dto.registration.RegistrationResponse;
import org.wise_economy.account_aggregator.repository.AccountRepository;
import org.wise_economy.account_aggregator.repository.UserRepository;
import org.wise_economy.account_aggregator.repository.UserToPerfiosTxnIdMappingRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class PerfiosRegistrationServiceImpl implements RegistrationService {

    @Value("${app.consent.callback.base.uri}")
    private String consentCallbackUri;

    private final UserRepository userRepository;
    private final AccountAggregator accountAggregator;
    private final UserToPerfiosTxnIdMappingRepository userToPerfiosTxnIdMappingRepository;
    private final AccountRepository accountRepository;

    @Override
    public RegistrationResponse registerUserAndCreateAccountAtAA(
        RegistrationRequest registrationRequest) {
        RegistrationResponse registrationResponse = new RegistrationResponse();
        User user = userRepository.findByMobileNumber(registrationRequest.getMobileNumber())
            .orElse(null);
        if (ObjectUtils.isEmpty(user)) {

            // user creation.
            User user1 = new User();
            user1.setGender(registrationRequest.getGender());
            user1.setMobileNumber(registrationRequest.getMobileNumber());
            user1.setName(
                registrationRequest.getFirstName() + " " + registrationRequest.getLastName());
            user1.setDob(registrationRequest.getDob());
            userRepository.save(user1);

            // call perfios to register the user.
            RegistrationPayload registrationPayload = new RegistrationPayload();
            registrationPayload.setUserMobileOrHandle(user1.getMobileNumber());
            UUID uuid = UUID.randomUUID();
            registrationPayload.setTxnId(uuid.toString());
            registrationPayload.setProfileId("wiseeconomy_deposit_profile");
            registrationPayload.setReturnURL(consentCallbackUri);
            org.wise_economy.account_aggregator.dto.perfios.registration.RegistrationResponse registrationResponse1 = accountAggregator
                .initiateRegistrationAndConsent(registrationPayload);
            user1.setPerfiosUserHandle(registrationResponse1.getUserId());
            user1.setPerfiosConsentHandle(registrationResponse1.getConsentHandle());
            userRepository.save(user1);

            // record the txn id with the userid.
            UserToPerfiosTxnMapping userToPerfiosTxnMapping = new UserToPerfiosTxnMapping();
            userToPerfiosTxnMapping.setUser(user1);
            userToPerfiosTxnMapping.setTxnId(uuid.toString());
            userToPerfiosTxnMapping.setType(TransactionType.Registration);
            userToPerfiosTxnIdMappingRepository.save(userToPerfiosTxnMapping);

            registrationResponse.setSuccess(true);
            registrationResponse.setUserId(user1.getId());
            registrationResponse.setThirdPartyUrl(registrationResponse1.getRedirectURL());
        } else {
            registrationResponse.setSuccess(true);
            registrationResponse.setUserId(user.getId());
        }
        return registrationResponse;
    }

    @Override
    public Boolean processConsentCallBackFromAA(ConsentCallbackResponse consentCallbackResponse) {

        UserToPerfiosTxnMapping txnMapping = userToPerfiosTxnIdMappingRepository
            .findByTxnId(consentCallbackResponse.getTxnId()).orElse(null);
        if (!ObjectUtils.isEmpty(txnMapping)) {
            User userInContext = txnMapping.getUser();
            userInContext.setPerfiosUserId(consentCallbackResponse.getUserId());
            userRepository.save(userInContext);
            consentCallbackResponse.getAccounts().forEach(account -> {
                Account account1 = new Account();
                account1.setFipId(account.getFipId());
                account1.setFiType(account.getFiType());
                account1.setAccountType(account.getAccType());
                account1.setLinkRefNumber(account.getLinkRefNumber());
                account1.setMaskedAccountNumber(account.getMaskedAccNumber());
                account1.setStatus(account.getStatus());
                account1.setUser(userInContext);
                accountRepository.save(account1);
            });
            return true;
        } else {
            return false;
        }
    }
}
