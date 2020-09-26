package org.example.model.builders;

import org.example.model.AccountModel;
import org.example.model.UserModel;

public class UserModelBuilder {
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private String emailAddress;
    private String account;

    public UserModelBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserModelBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserModelBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserModelBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserModelBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserModelBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserModelBuilder setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public UserModelBuilder setAccount(String account) {
        this.account = account;
        return this;
    }

    public UserModel createUserModel() {
        return new UserModel(id, username, password, name, surname, phoneNumber, emailAddress, account);
    }
}