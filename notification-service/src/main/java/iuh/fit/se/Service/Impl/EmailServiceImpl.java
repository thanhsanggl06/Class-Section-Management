package iuh.fit.se.Service.Impl;

import iuh.fit.se.event.RegisterSuccessEvent;
import iuh.fit.se.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    public JavaMailSender emailSender;

    @Override
    public String sendHtmlEmail(RegisterSuccessEvent request) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        // Info
        String subjectName = request.getSubjectName();
        double tuition = request.getTuition();

        // Tạo phần HTML cho email
        StringBuilder htmlMsg = new StringBuilder("<h3>Thông tin đăng ký:</h3>");
        htmlMsg.append("<p><strong>Tên môn học:</strong> ").append(subjectName).append("</p>");
        htmlMsg.append("<p><strong>Học phí:</strong> ").append(tuition).append("</p>");


        message.setContent(htmlMsg.toString(), "text/html;charset=UTF-8");

        helper.setTo("thanhsangglp06@gmail.com");
        helper.setSubject("Đăng ký học phần thành công");

        this.emailSender.send(message);

        return "Email Sent!";
    }
}
