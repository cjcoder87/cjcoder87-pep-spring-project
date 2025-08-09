package com.example.service.util;

import org.springframework.stereotype.Component;

import com.example.entity.Account;
import com.example.exception.InvalidInputException;

@Component
public class AccountValidator {

    public void isValidRegistration(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank())
            throw new InvalidInputException("Username not valid");
        if (account.getPassword() == null || account.getPassword().length() < 4)
            throw new InvalidInputException("Password not valid");
    }

    public void isValidLogin(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank())
            throw new IllegalArgumentException("Username is Blank");
        if (account.getPassword() == null || account.getPassword().isBlank())
            throw new IllegalArgumentException("Password is Blank");
    }
}
