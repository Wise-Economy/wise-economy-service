package org.wise_economy.account_aggregator.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wise_economy.account_aggregator.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMobileNumber(String phoneNumber);
}
