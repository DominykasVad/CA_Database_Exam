package org.example.model.builders;

import org.example.model.TransactionModel;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class TransactionModelBuilder {
    private int id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private Date date;
    private Time time;

    public TransactionModelBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TransactionModelBuilder setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
        return this;
    }

    public TransactionModelBuilder setToAccount(String toAccount) {
        this.toAccount = toAccount;
        return this;
    }

    public TransactionModelBuilder setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransactionModelBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public TransactionModelBuilder setTime(Time time) {
        this.time = time;
        return this;
    }

    public TransactionModel createTransactionModel() {
        return new TransactionModel(id, fromAccount, toAccount, amount, date, time);
    }
}