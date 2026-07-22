package com.rkrtransports.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BookingEnquiryRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^(\\+614\\d{8}|04\\d{8})$",
        message = "Please enter a valid Australian mobile number (e.g. +61 412 345 678 or 0412 345 678)"
    )
    private String phone;

    @NotBlank(message = "Service type is required")
    private String serviceType;

    @NotBlank(message = "Pickup address is required")
    @Size(max = 255, message = "Pickup address must not exceed 255 characters")
    private String pickupAddress;

    @NotBlank(message = "Delivery address is required")
    @Size(max = 255, message = "Delivery address must not exceed 255 characters")
    private String deliveryAddress;

    private String preferredDate;

    @Size(max = 1000, message = "Message must not exceed 1000 characters")
    private String message;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone != null ? phone.replaceAll("\\s", "") : null; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getPreferredDate() { return preferredDate; }
    public void setPreferredDate(String preferredDate) { this.preferredDate = preferredDate; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
