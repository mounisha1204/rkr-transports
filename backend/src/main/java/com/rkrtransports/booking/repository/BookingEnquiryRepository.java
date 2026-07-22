package com.rkrtransports.booking.repository;

import com.rkrtransports.booking.entity.BookingEnquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingEnquiryRepository extends JpaRepository<BookingEnquiry, Long> {
}
