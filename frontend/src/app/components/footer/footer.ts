import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-footer',
  imports: [RouterLink],
  templateUrl: './footer.html',
  styleUrl: './footer.scss'
})
export class FooterComponent {
  private router = inject(Router);
  year = new Date().getFullYear();

  navigateTo(fragment?: string) {
    const onHome = this.router.url === '/' || this.router.url === '';
    if (onHome) {
      if (fragment) {
        document.getElementById(fragment)?.scrollIntoView({ behavior: 'smooth', block: 'start' });
      } else {
        window.scrollTo({ top: 0, behavior: 'smooth' });
      }
    } else {
      this.router.navigate(['/'], { fragment });
    }
  }
}
