import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HomerService } from '../../../services/homer/homer.service';
import { BookingService } from '../../../services/booking/booking.service';
import { Booking } from '../../../models/booking.model';

@Component({
  selector: 'app-homer-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './homer-dashboard.component.html',
  styleUrl: './homer-dashboard.component.scss'
})
export class HomerDashboardComponent implements OnInit {
  dashboardData: any = null;
  bookings: Booking[] = [];
  loading = true;
  homerService = inject(HomerService);
  bookingService = inject(BookingService);

  ngOnInit() {
    this.homerService.getDashboard().subscribe({
      next: (data) => {
        this.dashboardData = data;
        this.loading = false;
        this.loadBookings();
      },
      error: () => this.loading = false
    });
  }

  loadBookings() {
    this.bookingService.getHomerBookings().subscribe({
      next: (data) => this.bookings = data,
      error: (err) => console.error(err)
    });
  }

  getStars(rating: number): number[] {
    return Array(5).fill(0).map((x, i) => i < Math.round(rating) ? 1 : 0);
  }
}
