import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CleanerDashboardService } from '../../../services/cleaner/cleaner-dashboard.service';

@Component({
  selector: 'app-cleaner-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cleaner-dashboard.component.html',
  styleUrl: './cleaner-dashboard.component.scss'
})
export class CleanerDashboardComponent implements OnInit {
  dashboardData: any = null;
  loading = true;
  service = inject(CleanerDashboardService);

  ngOnInit() {
    this.service.getDashboard().subscribe({
      next: (data) => {
        this.dashboardData = data;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  getStars(rating: number): number[] {
    return Array(5).fill(0).map((x, i) => i < Math.round(rating) ? 1 : 0);
  }
}
