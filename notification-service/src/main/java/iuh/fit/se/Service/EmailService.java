package iuh.fit.se.Service;

import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {
    String sendHtmlEmail(Map<String, Object> registerSuccessEvent) throws MessagingException;
}
