import { Component } from '@angular/core';

@Component({
  selector: 'app-services',
  templateUrl: './services.html',
  styleUrl: './services.scss'
})
export class ServicesComponent {
  services = [
    {
      title: 'Home Relocation',
      desc: 'Complete household moving services with careful packing, loading and timely delivery to your new home.',
      color: '#1a2b5f',
      bg: 'rgba(26,43,95,0.08)'
    },
    {
      title: 'Furniture Transport',
      desc: 'Specialised furniture transport with protective wrapping and expert handling for all furniture types.',
      color: '#f47c20',
      bg: 'rgba(244,124,32,0.08)'
    },
    {
      title: 'Furniture Assembly',
      desc: 'Professional furniture assembly and disassembly service to make your move or setup stress-free.',
      color: '#16a085',
      bg: 'rgba(22,160,133,0.08)'
    }
  ];
}
