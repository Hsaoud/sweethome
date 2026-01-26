import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-register-cleaner',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register-cleaner.component.html',
  styleUrl: './register-cleaner.component.scss'
})
export class RegisterCleanerComponent {
  formData = {
    email: '',
    password: '',
    confirmPassword: '',
    firstName: '',
    lastName: '',
    phone: '',
    headline: '',
    bio: '',
    city: '',
    hourlyRate: null,
    experienceYears: null,
    categoryIds: [],
    skillIds: []
  };
  error = '';

  authService = inject(AuthService);
  router = inject(Router);

  onSubmit() {
    if (this.formData.password !== this.formData.confirmPassword) {
      this.error = 'Passwords do not match';
      return;
    }

    this.authService.registerCleaner(this.formData).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.error = err.error?.message || 'Registration failed';
      }
    });
  }
}
