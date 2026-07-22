import { Component } from '@angular/core';

@Component({
  selector: 'app-about',
  templateUrl: './about.html',
  styleUrl: './about.scss'
})
export class AboutComponent {
  features = [
    { icon: '🗺️', title: 'Tasmania Wide Transport', desc: 'Servicing all regions across Tasmania with reliable and timely logistics solutions.' },
    { icon: '🔒', title: 'Safe & Secure Delivery', desc: 'Your goods are handled with utmost care using professional packing and secure transport.' },
    { icon: '👥', title: 'Experienced Team', desc: 'Our licensed, professional team brings years of experience in safe cargo transportation.' },
    { icon: '💰', title: 'Affordable Pricing', desc: 'Transparent, competitive pricing with no hidden fees. Quality service at the best value.' }
  ];
}
