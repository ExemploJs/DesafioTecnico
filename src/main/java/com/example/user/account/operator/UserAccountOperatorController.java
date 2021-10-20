package com.example.user.account.operator;

import com.example.user.account.operator.request.BillRequest;
import com.example.user.account.operator.request.TransferRequest;
import com.example.user.account.operator.service.UserAccountOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class UserAccountOperatorController {

    private final UserAccountOperatorService service;

    @Autowired
    public UserAccountOperatorController(final UserAccountOperatorService service) {
        this.service = service;
    }

    @PutMapping("/account/{userId}/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public void withdraw(@PathVariable("userId") final Long userId, final BigDecimal value) {
        this.service.withdraw(userId, value);
    }

    @PutMapping("/account/{userId}/deposit")
    @ResponseStatus(HttpStatus.OK)
    public void deposit(@PathVariable("userId") final Long userId, final BigDecimal value) {
        this.service.deposit(userId, value);
    }

    @PutMapping("/account/{fromUserId}/transfer/{toUserId}")
    @ResponseStatus(HttpStatus.OK)
    public void transfer(@PathVariable("fromUserId") final Long fromUserId,
                         @PathVariable("toUserId") final Long toUserId,
                         @RequestBody final TransferRequest transferRequest) {
        this.service.transfer(fromUserId, toUserId, transferRequest);
    }

    @PutMapping("/account/{userId}/bill")
    @ResponseStatus(HttpStatus.OK)
    public void payBill(@PathVariable("userId") final Long userId,
                        @RequestBody final BillRequest billRequest) {
        this.service.payBill(userId, billRequest);
    }

}
