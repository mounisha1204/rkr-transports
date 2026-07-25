package com.rkrtransports.booking.repository;

import com.rkrtransports.booking.entity.BookingEnquiry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingEnquiryRepository extends MongoRepository<BookingEnquiry, String> {
}
