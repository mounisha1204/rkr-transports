import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BookingForm } from './booking-form.service';

export interface ApiResponse {
  success: boolean;
  message: string;
}

export interface BookingPayload {
  fullName: string;
  email: string;
  phone: string;
  serviceType: string;
  pickupAddress: string;
  deliveryAddress: string;
  preferredDate: string;
  message: string;
}

@Injectable({ providedIn: 'root' })
export class BookingApiService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/bookings/enquiry';

  submitEnquiry(form: BookingForm): Observable<ApiResponse> {
    const payload: BookingPayload = {
      fullName: form.name,
      email: form.email,
      phone: form.phone,
      serviceType: form.serviceType,
      pickupAddress: form.pickupAddress,
      deliveryAddress: form.deliveryAddress,
      preferredDate: form.preferredDate,
      message: form.message
    };
    return this.http.post<ApiResponse>(this.apiUrl, payload);
  }
}
