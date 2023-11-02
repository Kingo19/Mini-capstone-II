package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalanceByUser(String username) {
        String sqlFindBalanceById= "select account.balance from account join tenmo_user on account.user_id = tenmo_user.user_id where tenmo_user.username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindBalanceById,username);
        BigDecimal balance = new BigDecimal(0.00);
        while (results.next()){
            balance = results.getBigDecimal("balance");
        }

        return balance;
    }
}
