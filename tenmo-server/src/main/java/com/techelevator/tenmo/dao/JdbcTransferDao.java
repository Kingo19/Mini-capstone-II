package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountModel;
import com.techelevator.tenmo.model.NewTransfer;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deductFrom(int userIdFrom, BigDecimal amount) {  //Updates the account that the transfer is coming from (user inputting the transfer)
        AccountModel transferFromAccount = new AccountModel(); //created a from account object
        String sqlSetTransferFromAccountData = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";//setting the values using postgres searching by the id number
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSetTransferFromAccountData, userIdFrom);
        while (results.next()) {

            transferFromAccount.setAccount_id(results.getInt("account_id"));
            transferFromAccount.setUser_id(results.getInt("user_id"));
            transferFromAccount.setBalance(results.getBigDecimal("balance"));
        }//no helper method but is basically our mapToRowSet
        String sqlUpdateUserIdFrom = "UPDATE account SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(sqlUpdateUserIdFrom, transferFromAccount.getBalance().subtract(amount), userIdFrom);//updates the amount in the account it's from


    }

    @Override
    public void addMoneyTo(int userIdTo, BigDecimal amount) { // set up SQL stuff to add money to the user specified in the front end
        AccountModel transferToAccount = new AccountModel(); //created a from account object
        String sqlSetTransferFromAccountData = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";//setting the values using postGres searching by the id number
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSetTransferFromAccountData, userIdTo);
        while (results.next()) {

            transferToAccount.setAccount_id(results.getInt("account_id"));
            transferToAccount.setUser_id(results.getInt("user_id"));
            transferToAccount.setBalance(results.getBigDecimal("balance"));

            String sqlUpdateUserIdTo = "UPDATE account SET balance = ? WHERE user_id = ?";
            jdbcTemplate.update(sqlUpdateUserIdTo, (transferToAccount.getBalance().add(amount)), userIdTo);

        }

    }
    @Override
    public NewTransfer addToTransferTable(NewTransfer newTransfer) {
        int fromAccountIdConverted = convertedAccountID(newTransfer.getFromUserId());
        int toAccountIdConverted = convertedAccountID(newTransfer.getToUserId());


        String sqlPostToTransfer = "insert into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)"
                + "values (2, 2, ?, ?, ?)";
        jdbcTemplate.update(sqlPostToTransfer, fromAccountIdConverted, toAccountIdConverted, newTransfer.getAmount());
        return newTransfer;

    }

    public List<Transfer> showTransfers(int userId) {
        List<Transfer> listOfTransfers = new ArrayList<>();

        String sqlGetTransfers = "select * from tenmo_user " +
                "JOIN account ON tenmo_user.user_id = account.user_id " +
                "JOIN transfer ON account.account_id = transfer.account_from OR account.account_id = transfer.account_to " +
                "WHERE account.account_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetTransfers, convertedAccountID(userId));
        while (results.next()) {
            Transfer transferHistory = mapRowToTransfer(results);
            listOfTransfers.add(transferHistory);

            if (transferHistory.getAccountTo() == convertedAccountID(userId)) {
                transferHistory.setOtherUser(findUsernameById(convertedUserID(transferHistory.getAccountFrom())));
                transferHistory.setWasSentToUs(true);
            } else {
                transferHistory.setOtherUser(findUsernameById(convertedUserID(transferHistory.getAccountTo())));
                transferHistory.setWasSentToUs(false);
            }

        }
        return listOfTransfers;

    }

    public Transfer getTransferById(int currentUser, int transferId) {
        String sqlShowTransferById = "SELECT * FROM transfer WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlShowTransferById, transferId);
        while (results.next()) {
            Transfer transferById = mapRowToTransfer(results);

            if (transferById.getAccountFrom() == convertedAccountID(currentUser)) {
                transferById.setWasSentToUs(false);
                transferById.setOtherUser(findUsernameById(convertedUserID(transferById.getAccountTo())));
                return transferById;

            } else {
                transferById.setWasSentToUs(true);
                transferById.setOtherUser(findUsernameById(convertedUserID(transferById.getAccountFrom())));
                return transferById;

            }
        } return null;
    }

    public int convertedAccountID(int userId) {
        int accountIdConverted = 0;
        String sqlConvertUserIdToAccountId = "SELECT account_id FROM account WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlConvertUserIdToAccountId, userId);
        while (results.next()) {
            accountIdConverted = results.getInt("account_id");
        }
        return accountIdConverted;

    }
    public int convertedUserID(int accountId) {
        int convertedUserId = 0;
        String sqlConvertAccountIdToUserId = "SELECT user_id FROM account WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlConvertAccountIdToUserId, accountId);
        while (results.next()) {
            convertedUserId = results.getInt("user_id");
        }
        return convertedUserId;

    }

    public String findUsernameById(int userId) {
        String username = "";
        String sql = "SELECT username FROM tenmo_user WHERE user_id = ?";
        username = jdbcTemplate.queryForObject(sql, String.class, userId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            username = results.getString("username");
        }
        return username;

    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));

        return transfer;
    }

}
