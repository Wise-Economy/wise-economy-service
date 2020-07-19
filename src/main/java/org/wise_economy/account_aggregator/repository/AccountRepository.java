package org.wise_economy.account_aggregator.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wise_economy.account_aggregator.domain.Account;
import org.wise_economy.account_aggregator.domain.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAccountsByUser(User user);
}
