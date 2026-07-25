package com.rkrtransports.booking.service;

import com.rkrtransports.booking.dto.BookingEnquiryRequest;
import com.rkrtransports.booking.repository.BookingEnquiryRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private static final String RESEND_API_URL = "https://api.resend.com/emails";

    private final TemplateEngine templateEngine;
    private final BookingEnquiryRepository repository;

    @Value("${booking.recipient.email}")
    private String recipientEmail;

    @Value("${email.api.key}")
    private String apiKey;

    @Value("${email.from}")
    private String fromEmail;

    private RestClient restClient;

    public EmailService(TemplateEngine templateEngine, BookingEnquiryRepository repository) {
        this.templateEngine = templateEngine;
        this.repository = repository;
    }

    @PostConstruct
    void init() {
        this.restClient = RestClient.builder()
                .baseUrl(RESEND_API_URL)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Async("emailTaskExecutor")
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

            log.info("[EMAIL] Generating Thymeleaf template...");
            String htmlContent = templateEngine.process("booking-enquiry-email", context);

            Map<String, Object> payload = Map.of(
                    "from", fromEmail,
                    "to", List.of(recipientEmail),
                    "reply_to", request.getEmail(),
                    "subject", "New Booking Enquiry - RK & R Transports and Logistics",
                    "html", htmlContent
            );

            log.info("[EMAIL] Sending email through Resend HTTPS API...");
            var response = restClient.post()
                    .body(payload)
                    .retrieve()
                    .toEntity(Map.class);

            log.info("[EMAIL] Email API response received. Status: {}", response.getStatusCode());
            log.info("[EMAIL] Email sent successfully to: {}", recipientEmail);

            repository.findById(enquiryId).ifPresent(e -> {
                e.setEmailSent(true);
                repository.save(e);
                log.info("[EMAIL] emailSent flag updated for enquiry id: {}", enquiryId);
            });

        } catch (RestClientResponseException e) {
            log.error("[EMAIL] Email API request failed. HTTP Status: {}, Response: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[EMAIL] Unexpected error during email send for enquiry {}: {}", enquiryId, e.getMessage(), e);
        }
    }
}
