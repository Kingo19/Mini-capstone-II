package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    UserDao userDao;

    public UserServiceImplementation(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByUserName(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public Double getAccountBalance(int userId) {
        return userDao.getAccountBalance(userId);
    }
}


