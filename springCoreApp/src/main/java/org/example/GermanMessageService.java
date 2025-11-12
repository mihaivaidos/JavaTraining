package org.example;

import org.springframework.stereotype.Component;

@Component("germanMessage")
public class GermanMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "Hallo, Spring!!!!";
    }
}
