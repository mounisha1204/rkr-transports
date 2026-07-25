package com.rkrtransports.booking.service;

import com.rkrtransports.booking.dto.BookingEnquiryRequest;
import com.rkrtransports.booking.entity.BookingEnquiry;
import com.rkrtransports.booking.repository.BookingEnquiryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookingEnquiryService {

    private static final Logger log = LoggerFactory.getLogger(BookingEnquiryService.class);

    private final BookingEnquiryRepository repository;
    private final EmailService emailService;

    public BookingEnquiryService(BookingEnquiryRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public void processEnquiry(BookingEnquiryRequest request) {
        BookingEnquiry enquiry = mapToEntity(request);
        enquiry.setEmailSent(false);
        enquiry = repository.save(enquiry);
        log.info("Booking enquiry saved to MongoDB with id: {}", enquiry.getId());

        boolean emailSent = emailService.sendBookingEnquiryEmail(request);
        if (emailSent) {
            enquiry.setEmailSent(true);
            repository.save(enquiry);
            log.info("emailSent updated to true for enquiry id: {}", enquiry.getId());
        } else {
            log.warn("Email failed — emailSent remains false for enquiry id: {}", enquiry.getId());
        }
    }

    private BookingEnquiry mapToEntity(BookingEnquiryRequest request) {
        BookingEnquiry enquiry = new BookingEnquiry();
        enquiry.setFullName(request.getFullName());
        enquiry.setEmail(request.getEmail());
        enquiry.setPhone(request.getPhone());
        enquiry.setServiceType(request.getServiceType());
        enquiry.setPickupAddress(request.getPickupAddress());
        enquiry.setDeliveryAddress(request.getDeliveryAddress());
        enquiry.setPreferredDate(request.getPreferredDate());
        enquiry.setMessage(request.getMessage());
        return enquiry;
    }
}
