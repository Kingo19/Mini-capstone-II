package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/account_balance")
public class BalanceController {
    UserService userService;
    // Create constructor
    public BalanceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public Double getAccountBalance(@PathVariable int userId) {
        return userService.getAccountBalance(userId);
    }

}


