import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-register-homer',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register-homer.component.html',
  styleUrl: './register-homer.component.scss'
})
export class RegisterHomerComponent {
  formData = {
    email: '',
    password: '',
    confirmPassword: '',
    firstName: '',
    lastName: '',
    phone: '',
    address: '',
    city: '',
    postalCode: ''
  };
  error = '';

  authService = inject(AuthService);
  router = inject(Router);

  onSubmit() {
    if (this.formData.password !== this.formData.confirmPassword) {
      this.error = 'Passwords do not match';
      return;
    }

    this.authService.registerHomer(this.formData).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.error = err.error?.message || 'Registration failed';
      }
    });
  }
}
