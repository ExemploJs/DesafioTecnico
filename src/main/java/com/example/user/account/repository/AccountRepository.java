package com.example.user.account.repository;

import com.example.user.account.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.user.id = ?1")
    Account findByUserId(final Long userId);
}
