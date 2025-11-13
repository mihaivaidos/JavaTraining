package org.example;

import org.springframework.stereotype.Component;

@Component("englishMessage")
public class EnglishMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "Hello from the EnglishMessageService!";
    }
}
