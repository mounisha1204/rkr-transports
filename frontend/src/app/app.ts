import { Component, inject } from '@angular/core';
import { Router, RouterOutlet, NavigationEnd } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar';
import { FooterComponent } from './components/footer/footer';
import { toSignal } from '@angular/core/rxjs-interop';
import { filter, map } from 'rxjs';

const LEGAL_ROUTES = ['/privacy-policy', '/terms'];

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent, FooterComponent],
  template: `
    @if (!isLegalPage()) { <app-navbar /> }
    <router-outlet />
    <app-footer />
  `,
  styles: [`:host { display: block; }`]
})
export class App {
  private router = inject(Router);

  isLegalPage = toSignal(
    this.router.events.pipe(
      filter(e => e instanceof NavigationEnd),
      map(e => LEGAL_ROUTES.some(r => (e as NavigationEnd).urlAfterRedirects.startsWith(r)))
    ),
    { initialValue: LEGAL_ROUTES.some(r => this.router.url.startsWith(r)) }
  );
}
