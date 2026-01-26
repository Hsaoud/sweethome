import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';

  authService = inject(AuthService);
  router = inject(Router);

  onSubmit() {
    this.authService.login({ username: this.email, password: this.password }).subscribe({
      next: (user) => {
        if (user.role === 'HOMER') {
          this.router.navigate(['/dashboard/homer']);
        } else if (user.role === 'CLEANER') {
          this.router.navigate(['/dashboard/cleaner']);
        } else {
          this.router.navigate(['/']);
        }
      },
      error: () => {
        this.error = 'Invalid email or password';
      }
    });
  }
}
