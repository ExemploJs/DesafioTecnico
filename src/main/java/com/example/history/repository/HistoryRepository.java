package com.example.history.repository;

import com.example.history.entity.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {

    @Query("select h from History h WHERE h.account.id = ?1")
    List<History> findByAccountId(final Long userId);
}
