import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CleanerService } from '../../../services/cleaner/cleaner.service';
import { BookingService } from '../../../services/booking/booking.service';
import { CleanerSearchResponseDto } from '../../../models/cleaner-search-response';

@Component({
  selector: 'app-cleaner-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './cleaner-list.component.html',
  styleUrl: './cleaner-list.component.scss'
})
export class CleanerListComponent implements OnInit {
  cleaners: CleanerSearchResponseDto[] = [];
  searchForm!: FormGroup;

  isModalOpen = false;
  selectedCleaner: CleanerSearchResponseDto | null = null;
  bookingSuccess = false;

  cleanerService = inject(CleanerService);
  bookingService = inject(BookingService);
  fb = inject(FormBuilder);

  ngOnInit() {
    this.searchForm = this.fb.group({
      city: ['', Validators.required],
      surface: [null, [Validators.required, Validators.min(10)]],
      date: ['', Validators.required],
      startTime: ['', Validators.required],
      endTime: ['', Validators.required]
    });
  }

  onSearch() {
    if (this.searchForm.valid) {
      const formValue = this.searchForm.value;
      const lat = 48.8566;
      const lng = 2.3522;

      this.cleanerService.searchGeo(
        lat,
        lng,
        formValue.surface,
        formValue.date,
        formValue.startTime,
        formValue.endTime
      ).subscribe(data => {
        this.cleaners = data;
      });
    }
  }

  openBookingModal(cleaner: CleanerSearchResponseDto) {
    if (!this.searchForm.valid) return;
    this.selectedCleaner = cleaner;
    this.isModalOpen = true;
    this.bookingSuccess = false;
  }

  closeModal() {
    this.isModalOpen = false;
    this.selectedCleaner = null;
  }

  confirmBooking() {
    if (!this.selectedCleaner || !this.searchForm.valid) return;

    const formValue = this.searchForm.value;
    this.bookingService.createBookingRequest({
      cleanerId: this.selectedCleaner.id,
      date: formValue.date,
      startTime: formValue.startTime,
      endTime: formValue.endTime,
      surface: formValue.surface
    }).subscribe(() => {
      this.bookingSuccess = true;
      setTimeout(() => {
        this.closeModal();
      }, 2000);
    });
  }
}
