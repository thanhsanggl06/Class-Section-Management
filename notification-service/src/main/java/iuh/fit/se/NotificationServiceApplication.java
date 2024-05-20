package iuh.fit.se;

import iuh.fit.se.Service.EmailService;
import iuh.fit.se.constant.Constant;
import iuh.fit.se.event.RegisterSuccessEvent;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class NotificationServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Autowired
    private EmailService emailService;
    @KafkaListener(topics = Constant.NOTIFICATION_TOPIC)
    public void sendEmail(RegisterSuccessEvent registerSuccessEvent) throws MessagingException {
        emailService.sendHtmlEmail(registerSuccessEvent);
    }
}
