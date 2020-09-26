package org.example.model.builders;

import org.example.model.AccountModel;

import java.math.BigDecimal;

public class AccountModelBuilder {
    private int id;
    private String iban;
    private BigDecimal balance;

    public AccountModelBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public AccountModelBuilder setIban(String iban) {
        this.iban = iban;
        return this;
    }

    public AccountModelBuilder setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public AccountModel createAccountModel() {
        return new AccountModel(id, iban, balance);
    }
}