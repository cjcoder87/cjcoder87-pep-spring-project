package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.InvalidMessageRequestException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.util.MessageValidator;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
    private final MessageValidator messageValidator;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository,
            MessageValidator messageValidator) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
        this.messageValidator = messageValidator;
    }

    public Message createMessage(Message message) {
        this.messageValidator.isValidMessage(message);
        if (message.getPostedBy() == null ||
                !this.accountRepository.existsById(message.getPostedBy()))
            throw new InvalidMessageRequestException("PostedBy must be a valid user");

        // Set time posted if needed (e.g., current epoch seconds)
        message.setTimePostedEpoch(message.getTimePostedEpoch());

        return this.messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return this.messageRepository.findAll();
    }

    public Optional<Message> getMessageByMessageId(Integer messageId) {
        return this.messageRepository.findById(messageId);
    }

    public int deleteMessageById(Integer messageId) {
        boolean exists = this.messageRepository.existsById(messageId);
        if (exists) {
            this.messageRepository.deleteById(messageId);
            return 1; // One row deleted
        }
        return 0; // Message did not exist
    }

}
