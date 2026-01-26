import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HomerService } from '../../../services/homer/homer.service';

@Component({
  selector: 'app-homer-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './homer-dashboard.component.html',
  styleUrl: './homer-dashboard.component.scss'
})
export class HomerDashboardComponent implements OnInit {
  dashboardData: any = null;
  loading = true;
  homerService = inject(HomerService);

  ngOnInit() {
    this.homerService.getDashboard().subscribe({
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
