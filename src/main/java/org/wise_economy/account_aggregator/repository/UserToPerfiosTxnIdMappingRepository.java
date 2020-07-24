package org.wise_economy.account_aggregator.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wise_economy.account_aggregator.domain.TransactionType;
import org.wise_economy.account_aggregator.domain.User;
import org.wise_economy.account_aggregator.domain.UserToPerfiosTxnMapping;

public interface UserToPerfiosTxnIdMappingRepository extends JpaRepository<UserToPerfiosTxnMapping, Long> {

    Optional<UserToPerfiosTxnMapping> findByTxnId(String txnId);

    Optional<UserToPerfiosTxnMapping> findByUserAndType(User user, TransactionType type);
}
