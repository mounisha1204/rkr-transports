import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-privacy-policy',
  imports: [],
  templateUrl: './privacy-policy.html',
  styleUrl: './privacy-policy.scss'
})
export class PrivacyPolicyComponent {
  private router = inject(Router);
  goBack() { this.router.navigate(['/booking-enquiry'], { queryParams: { scrollToForm: '1' } }); }
}
