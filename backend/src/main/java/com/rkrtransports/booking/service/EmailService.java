package com.rkrtransports.booking.service;

import com.rkrtransports.booking.dto.BookingEnquiryRequest;
import com.rkrtransports.booking.repository.BookingEnquiryRepository;
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
    private final BookingEnquiryRepository repository;

    @Value("${booking.recipient.email}")
    private String recipientEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine, BookingEnquiryRepository repository) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.repository = repository;
    }

    @Async
    public void sendBookingEnquiryEmail(BookingEnquiryRequest request, String enquiryId) {
        log.info("[EMAIL] Starting email send for enquiry from: {}", request.getEmail());
        log.info("[EMAIL] Recipient: {}, From: {}", recipientEmail, fromEmail);
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

            log.info("[EMAIL] Processing Thymeleaf template...");
            String htmlContent = templateEngine.process("booking-enquiry-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(recipientEmail);
            helper.setReplyTo(request.getEmail());
            helper.setSubject("New Booking Enquiry - RK & R Transports and Logistics");
            helper.setText(htmlContent, true);

            log.info("[EMAIL] Connecting to SMTP and sending...");
            mailSender.send(mimeMessage);
            log.info("[EMAIL] Successfully sent to: {}", recipientEmail);

            repository.findById(enquiryId).ifPresent(e -> {
                e.setEmailSent(true);
                repository.save(e);
                log.info("[EMAIL] emailSent flag updated for enquiry id: {}", enquiryId);
            });
        } catch (MessagingException e) {
            log.error("[EMAIL] MessagingException for {}: {}", request.getEmail(), e.getMessage(), e);
        } catch (RuntimeException e) {
            log.error("[EMAIL] RuntimeException (likely SMTP/network) for {}: {}", request.getEmail(), e.getMessage(), e);
        }
    }
}
