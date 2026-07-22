import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-terms',
  imports: [],
  templateUrl: './terms.html',
  styleUrl: './terms.scss'
})
export class TermsComponent {
  private router = inject(Router);
  goBack() { this.router.navigate(['/booking-enquiry'], { queryParams: { scrollToForm: '1' } }); }
}
