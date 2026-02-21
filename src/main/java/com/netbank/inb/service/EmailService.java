package com.netbank.inb.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendHtmlEmail(String toEmail, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = isHtml
            helper.setFrom("no-reply@inetbank.com");

            mailSender.send(message);
            log.info("HTML email sent to {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send HTML email", e);
        }
    }

    @Async // <--- THIS IS KEY. It prevents the Admin UI from freezing.
    public void sendApprovalEmail(String toEmail, String userName, String accountNumber) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Welcome to iNetBank - Account Approved!");
            message.setText("Dear " + userName + ",\n\n" +
                    "Congratulations! Your account application has been approved.\n" +
                    "Your new Account Number is: " + accountNumber + "\n\n" +
                    "You can now login to your dashboard using your registered credentials.\n\n" +
                    "Regards,\n" +
                    "iNetBank Team");

            mailSender.send(message);
            log.info("Approval email sent to {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send email to {}", toEmail, e);
            // In a real bank, you might save this to a 'failed_emails' table to retry later
        }
    }
}