import { Injectable, signal } from '@angular/core';

export interface BookingForm {
  name: string;
  email: string;
  phone: string;
  serviceType: string;
  pickupAddress: string;
  deliveryAddress: string;
  preferredDate: string;
  message: string;
  privacyConsent: boolean;
}

const STORAGE_KEY = 'rkr_booking_form';

export const EMPTY_FORM: BookingForm = {
  name: '',
  email: '',
  phone: '',
  serviceType: '',
  pickupAddress: '',
  deliveryAddress: '',
  preferredDate: '',
  message: '',
  privacyConsent: false
};

@Injectable({ providedIn: 'root' })
export class BookingFormService {
  private _form = signal<BookingForm>(this._load());

  get form() { return this._form(); }

  save(form: BookingForm) {
    this._form.set({ ...form });
    sessionStorage.setItem(STORAGE_KEY, JSON.stringify(form));
  }

  clear() {
    this._form.set({ ...EMPTY_FORM });
    sessionStorage.removeItem(STORAGE_KEY);
  }

  private _load(): BookingForm {
    try {
      const raw = sessionStorage.getItem(STORAGE_KEY);
      return raw ? { ...EMPTY_FORM, ...JSON.parse(raw) } : { ...EMPTY_FORM };
    } catch {
      return { ...EMPTY_FORM };
    }
  }
}
