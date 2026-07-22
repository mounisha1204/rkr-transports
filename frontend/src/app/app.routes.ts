import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';

export const routes: Routes = [
  { path: '', component: HomeComponent, title: 'RK & R Transports and Logistics - Home' },
  { path: 'booking-enquiry', loadComponent: () => import('./pages/booking-enquiry/booking-enquiry').then(m => m.BookingEnquiryComponent), title: 'Book a Transport - RK & R Transports and Logistics' },
  { path: 'privacy-policy', loadComponent: () => import('./pages/privacy-policy/privacy-policy').then(m => m.PrivacyPolicyComponent), title: 'Privacy Policy - RK & R Transports and Logistics' },
  { path: 'terms', loadComponent: () => import('./pages/terms/terms').then(m => m.TermsComponent), title: 'Terms & Conditions - RK & R Transports and Logistics' },
  { path: '**', redirectTo: '' }
];
