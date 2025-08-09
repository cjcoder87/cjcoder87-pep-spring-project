package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.InvalidInputException;
import com.example.exception.UserNotAuthorizedException;
import com.example.exception.UsernameAlreadyExistsException;
import com.example.repository.AccountRepository;
import com.example.service.util.AccountValidator;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountValidator accountValidator;

    public AccountService(AccountRepository accountRepository, AccountValidator accountValidator) {
        this.accountRepository = accountRepository;
        this.accountValidator = accountValidator;
    }

    public Account registerAccount(Account account) {
        if (this.accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username '" + account.getUsername() + "' already exists");
        }

        this.accountValidator.isValidRegistration(account);
        return this.accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        Optional<Account> foundAccount = accountRepository.findByUsername(account.getUsername());

        if (foundAccount.isEmpty()) {
            throw new UserNotAuthorizedException("Invalid username or password");
        }

        Account existingAccount = foundAccount.get();

        if (!existingAccount.getPassword().equals(account.getPassword())) {
            throw new UserNotAuthorizedException("Invalid password");
        }

        return existingAccount;
    }

}
