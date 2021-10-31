package com.example.user.account.repository;

import com.example.user.account.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.user.id = ?1")
    List<Account> findByUserId(final Long userId);

    @Query("SELECT a FROM Account a WHERE a.user.id = ?1 AND a.active = true")
    Account findActiveByUserId(final Long userId);
}
