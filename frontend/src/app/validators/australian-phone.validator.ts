import { Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator } from '@angular/forms';

// Strips spaces then validates: +614XXXXXXXX or 04XXXXXXXX
const AU_MOBILE_RE = /^(\+614\d{8}|04\d{8})$/;

export function validateAustralianPhone(value: string): boolean {
  if (!value) return false; // field is now required
  return AU_MOBILE_RE.test(value.replace(/\s/g, ''));
}

@Directive({
  selector: '[australianPhone]',
  standalone: true,
  providers: [{ provide: NG_VALIDATORS, useExisting: AustralianPhoneValidatorDirective, multi: true }]
})
export class AustralianPhoneValidatorDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    return validateAustralianPhone(control.value) ? null : { australianPhone: true };
  }
}
