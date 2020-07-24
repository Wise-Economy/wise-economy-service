package org.wise_economy.account_aggregator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.wise_economy.account_aggregator.client.web.AccountAggregator;
import org.wise_economy.account_aggregator.domain.Account;
import org.wise_economy.account_aggregator.domain.TransactionType;
import org.wise_economy.account_aggregator.domain.User;
import org.wise_economy.account_aggregator.domain.UserToPerfiosTxnMapping;
import org.wise_economy.account_aggregator.dto.perfios.datafetch.FetchTransactionsPayload;
import org.wise_economy.account_aggregator.dto.perfios.datafetch.InitiateDataFetch;
import org.wise_economy.account_aggregator.dto.transactions.DataFetchRequest;
import org.wise_economy.account_aggregator.repository.AccountRepository;
import org.wise_economy.account_aggregator.repository.UserRepository;
import org.wise_economy.account_aggregator.repository.UserToPerfiosTxnIdMappingRepository;

@Service
@RequiredArgsConstructor
public class GetDataFromPerfios implements GetDataFromAA {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountAggregator accountAggregator;
    private final UserToPerfiosTxnIdMappingRepository userToPerfiosTxnIdMappingRepository;

    @Override
    public Boolean getDataFromAA(DataFetchRequest dataFetchRequest) {
        User user = userRepository.findById(dataFetchRequest.getUserId()).orElse(null);
        if (!ObjectUtils.isEmpty(user)) {
            List<Account> accounts = accountRepository.findAccountsByUser(user);
            // create a initiate call to perfios.
            InitiateDataFetch initiateDataFetch = new InitiateDataFetch();
            initiateDataFetch.setUserId(user.getPerfiosUserId());
            initiateDataFetch.setProfileId("wiseeconomy_deposit_profile");
            UUID uuid = UUID.randomUUID();
            initiateDataFetch.setTxnId(uuid.toString());
            List<org.wise_economy.account_aggregator.dto.perfios.callback.Account> accounts1 = new ArrayList<>();
            accounts.forEach(account -> {
                org.wise_economy.account_aggregator.dto.perfios.callback.Account tempAccount = new org.wise_economy.account_aggregator.dto.perfios.callback.Account();
                tempAccount.setFipId(account.getFipId());
                tempAccount.setFiType(account.getFiType());
                tempAccount.setMaskedAccNumber(account.getMaskedAccountNumber());
                accounts1.add(tempAccount);
            });
            if (accounts1.size() == 0) {
                return false;
            }
            initiateDataFetch.setAccounts(accounts1);
            return accountAggregator.initiateDataFetch(initiateDataFetch);
        } else {
            return false;
        }
    }

    @Override
    public Boolean getTransactionsFromAA(Long userId) {
        User userInContext = userRepository.findById(userId).orElse(null);
        if (!ObjectUtils.isEmpty(userInContext)) {
            UserToPerfiosTxnMapping userToPerfiosTxnMapping = userToPerfiosTxnIdMappingRepository
                .findByUserAndType(userInContext, TransactionType.Registration).orElse(null);
            if (!ObjectUtils.isEmpty(userToPerfiosTxnMapping)) {
                String txnid = userToPerfiosTxnMapping.getTxnId();
                FetchTransactionsPayload fetchTransactionsPayload = new FetchTransactionsPayload();
                fetchTransactionsPayload.setTxnId(txnid);
                accountAggregator.getTransactions(fetchTransactionsPayload);
            }
        }
        return true;
    }
}
