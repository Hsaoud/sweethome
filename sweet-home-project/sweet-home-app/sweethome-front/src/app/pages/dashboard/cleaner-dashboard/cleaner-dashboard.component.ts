import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CleanerDashboardService } from '../../../services/cleaner/cleaner-dashboard.service';
import { BookingService } from '../../../services/booking/booking.service';
import { Booking, BookingStatus } from '../../../models/booking.model';

@Component({
  selector: 'app-cleaner-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cleaner-dashboard.component.html',
  styleUrl: './cleaner-dashboard.component.scss'
})
export class CleanerDashboardComponent implements OnInit {
  dashboardData: any = null;
  bookings: Booking[] = [];
  loading = true;

  service = inject(CleanerDashboardService);
  bookingService = inject(BookingService);

  ngOnInit() {
    this.service.getDashboard().subscribe({
      next: (data) => {
        this.dashboardData = data;
        this.loading = false;
        this.loadBookings();
      },
      error: () => this.loading = false
    });
  }

  loadBookings() {
    this.bookingService.getCleanerBookings().subscribe({
      next: (data) => this.bookings = data,
      error: (err) => console.error(err)
    });
  }

  updateBookingStatus(bookingId: number, status: BookingStatus) {
    this.bookingService.updateBookingStatus(bookingId, status).subscribe({
      next: (updatedBooking) => {
        const index = this.bookings.findIndex(b => b.id === bookingId);
        if (index !== -1) {
          this.bookings[index] = updatedBooking;
        }
      },
      error: (err) => console.error(err)
    });
  }

  getStars(rating: number): number[] {
    return Array(5).fill(0).map((x, i) => i < Math.round(rating) ? 1 : 0);
  }
}
