package com.example.service.util;

import org.springframework.stereotype.Component;

import com.example.entity.Message;
import com.example.exception.InvalidMessageRequestException;

@Component
public class MessageValidator {

    public void isValidMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank())
            throw new InvalidMessageRequestException("Message text cannot be blank");
        if (message.getMessageText().length() > 255)
            throw new InvalidMessageRequestException("Message text cannot exceed 255 characters");
    }
}
