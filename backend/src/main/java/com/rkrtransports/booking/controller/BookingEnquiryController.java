package com.rkrtransports.booking.controller;

import com.rkrtransports.booking.dto.ApiResponse;
import com.rkrtransports.booking.dto.BookingEnquiryRequest;
import com.rkrtransports.booking.service.BookingEnquiryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingEnquiryController {

    private static final Logger log = LoggerFactory.getLogger(BookingEnquiryController.class);

    private final BookingEnquiryService bookingEnquiryService;

    public BookingEnquiryController(BookingEnquiryService bookingEnquiryService) {
        this.bookingEnquiryService = bookingEnquiryService;
    }

    @PostMapping("/enquiry")
    public ResponseEntity<ApiResponse> submitEnquiry(@Valid @RequestBody BookingEnquiryRequest request) {
        log.info("Received booking enquiry from: {}", request.getEmail());
        bookingEnquiryService.processEnquiry(request);
        return ResponseEntity.ok(new ApiResponse(true,
                "Thank you! Your booking enquiry has been submitted successfully. Our team will contact you shortly."));
    }
}
