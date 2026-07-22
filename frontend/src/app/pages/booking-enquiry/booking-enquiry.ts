import { Component, signal, inject, OnInit } from '@angular/core';
import { RouterLink, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AustralianPhoneValidatorDirective } from '../../validators/australian-phone.validator';
import { BookingFormService, BookingForm } from '../../services/booking-form.service';
import { BookingApiService } from '../../services/booking-api.service';

@Component({
  selector: 'app-booking-enquiry',
  imports: [RouterLink, FormsModule, AustralianPhoneValidatorDirective],
  templateUrl: './booking-enquiry.html',
  styleUrl: './booking-enquiry.scss'
})
export class BookingEnquiryComponent implements OnInit {
  private bookingService = inject(BookingFormService);
  private bookingApi = inject(BookingApiService);
  private route = inject(ActivatedRoute);

  submitted = signal(false);
  loading = signal(false);
  errorMessage = signal<string | null>(null);
  today = new Date().toISOString().split('T')[0];

  form: BookingForm = { ...this.bookingService.form };

  services = [
    'Home Relocation',
    'Furniture Transport',
    'Furniture Assembly'
  ];

  ngOnInit() {
    this.form = { ...this.bookingService.form };
    if (this.route.snapshot.queryParamMap.get('scrollToForm')) {
      setTimeout(() => {
        document.getElementById('enquiry-form')?.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }, 100);
    }
  }

  onFormChange() {
    this.bookingService.save(this.form);
  }

  onSubmit() {
    this.loading.set(true);
    this.errorMessage.set(null);

    this.bookingApi.submitEnquiry(this.form).subscribe({
      next: () => {
        this.bookingService.clear();
        this.loading.set(false);
        this.submitted.set(true);
      },
      error: (err) => {
        this.loading.set(false);
        const msg = err?.error?.message;
        this.errorMessage.set(msg || 'Something went wrong while submitting your enquiry. Please try again later.');
      }
    });
  }

  resetForm() {
    this.bookingService.clear();
    this.form = { ...this.bookingService.form };
    this.submitted.set(false);
    this.errorMessage.set(null);
  }
}
