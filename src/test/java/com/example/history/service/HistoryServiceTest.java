package com.example.history.service;


import com.example.history.repository.HistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HistoryServiceTest {

    @InjectMocks
    private HistoryService historyService;

    @Mock
    private HistoryRepository repository;

    private long accountId;

    @Test
    public void findByAccountId() {
        givenAccountId();
        whenFindByAccountId();
        thenShouldHaveFound();
    }

    private void givenAccountId() {
        this.accountId = 1L;
    }

    private void whenFindByAccountId() {
        this.historyService.findByAccountId(this.accountId);
    }

    private void thenShouldHaveFound() {
        verify(this.repository, times(1)).findByAccountId(this.accountId);
    }
}