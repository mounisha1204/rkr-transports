import { Component } from '@angular/core';
import { HeroComponent } from '../../components/hero/hero';
import { AboutComponent } from '../../components/about/about';
import { ServicesComponent } from '../../components/services/services';
import { ContactComponent } from '../../components/contact/contact';

@Component({
  selector: 'app-home',
  imports: [HeroComponent, AboutComponent, ServicesComponent, ContactComponent],
  template: `
    <main id="main-content">
      <app-hero />
      <app-about />
      <app-services />
      <app-contact />
    </main>
  `
})
export class HomeComponent {}
