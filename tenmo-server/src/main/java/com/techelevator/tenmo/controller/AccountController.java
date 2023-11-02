package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.NewTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

    @Autowired
    private AccountDao accountDao;


    @Autowired
    private UserDao userDAO;

    @Autowired
    private TransferDao transferDAO;

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        BigDecimal balance = new BigDecimal(String.valueOf(accountDao.getBalanceByUser(principal.getName())));
        return balance;
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public NewTransfer newBalance(@RequestBody NewTransfer newTransfer) {
        transferDAO.deductFrom(newTransfer.getFromUserId(), newTransfer.getAmount());
        transferDAO.addMoneyTo(newTransfer.getToUserId(), newTransfer.getAmount());
        transferDAO.addToTransferTable(newTransfer);

        return newTransfer;
    }

}
