package com.example.user.history.repository;

import com.example.user.history.model.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {

    @Query("select h from History h WHERE h.user.id = ?1")
    List<History> findByUserId(final Long userId);
}
