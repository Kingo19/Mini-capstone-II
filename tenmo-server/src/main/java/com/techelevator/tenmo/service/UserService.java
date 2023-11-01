package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.User;

public interface UserService {
    User getUserById(int userId);
    User getUserByUserName(String username);
    Double getAccountBalance(int userId);

}