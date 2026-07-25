package com.rkrtransports.booking.service;

import com.rkrtransports.booking.dto.BookingEnquiryRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${booking.recipient.email}")
    private String recipientEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendBookingEnquiryEmail(BookingEnquiryRequest request) {
        log.info("Sending booking enquiry email for: {}", request.getEmail());
        try {
            Context context = new Context();
            context.setVariable("fullName", request.getFullName());
            context.setVariable("email", request.getEmail());
            context.setVariable("phone", request.getPhone() != null && !request.getPhone().isBlank() ? request.getPhone() : "Not provided");
            context.setVariable("serviceType", request.getServiceType());
            context.setVariable("pickupAddress", request.getPickupAddress());
            context.setVariable("deliveryAddress", request.getDeliveryAddress());
            context.setVariable("preferredDate", request.getPreferredDate() != null && !request.getPreferredDate().isBlank() ? request.getPreferredDate() : "Not specified");
            context.setVariable("message", request.getMessage() != null && !request.getMessage().isBlank() ? request.getMessage() : "None");
            context.setVariable("submittedAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")));

            String htmlContent = templateEngine.process("booking-enquiry-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(recipientEmail);
            helper.setReplyTo(request.getEmail());
            helper.setSubject("New Booking Enquiry - RK & R Transports and Logistics");
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("Booking enquiry email sent successfully for: {}", request.getEmail());
        } catch (MessagingException | RuntimeException e) {
            log.error("Failed to send booking enquiry email for: {} — {}", request.getEmail(), e.getMessage(), e);
        }
    }
}
