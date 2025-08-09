package com.example.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.UsernameAlreadyExistsException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        return ResponseEntity.ok(this.accountService.registerAccount(account));
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        return ResponseEntity.ok(this.accountService.loginAccount(account));
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        Message createdMessage = this.messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok(this.messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        Optional<Message> message = this.messageService.getMessageByMessageId(messageId);
        if (message.isPresent()) {
            // ResponseEntity will return a 200 if message exists
            return ResponseEntity.ok(message.get());
        } else {
            // .build will return empty response as 200 instead of a 204
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer messageId) {
        int rowsDeleted = this.messageService.deleteMessageById(messageId);
        if (rowsDeleted == 1) {
            // Return 1 in body
            return ResponseEntity.ok(rowsDeleted);
        } else {
            // Empty body with 200
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageText(
            @PathVariable int messageId,
            @RequestBody Map<String, String> body) {

        String messageText = body.get("messageText");
        int rowsUpdated = this.messageService.updateMessageText(messageId, messageText);

        return ResponseEntity.ok(rowsUpdated);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> messages = this.messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.ok(messages);
    }
}
