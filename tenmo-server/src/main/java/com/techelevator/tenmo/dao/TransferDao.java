package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.NewTransfer;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    void deductFrom(int userIdFrom, BigDecimal amount);

    void addMoneyTo(int userIdTo, BigDecimal amount);

    NewTransfer addToTransferTable(NewTransfer newTransfer);


    List<Transfer> showTransfers(int userId);

    Transfer getTransferById(int transferId, int userId);

    void requestMoney(int userIdFrom, int userIdTo, BigDecimal amount);

}


