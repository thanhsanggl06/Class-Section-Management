package iuh.fit.se.Service;

import iuh.fit.se.event.RegisterSuccessEvent;
import jakarta.mail.MessagingException;

public interface EmailService {
    String sendHtmlEmail(RegisterSuccessEvent request) throws MessagingException;
}
