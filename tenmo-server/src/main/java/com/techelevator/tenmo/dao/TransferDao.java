package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.NewTransfer;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    void deductFrom(int userIdFrom, BigDecimal amount);

    void addMoneyTo(int userIdTo, BigDecimal amount);

    NewTransfer addToTransferTable(NewTransfer newTransfer);

    public interface TransferDAO {
        void deductFrom(int accountFrom, BigDecimal amount);

        void addMoneyTo(int accountTo, BigDecimal amount);

        NewTransfer addToTransferTable(NewTransfer newTransfer);

    }
}
