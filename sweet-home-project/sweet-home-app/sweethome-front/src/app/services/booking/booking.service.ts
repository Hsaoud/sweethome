import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Booking, BookingRequestDto, BookingStatus } from '../../models/booking.model';

@Injectable({
    providedIn: 'root'
})
export class BookingService {
    private apiUrl = 'http://localhost:8080/api';

    constructor(private http: HttpClient) { }

    createBookingRequest(request: BookingRequestDto): Observable<Booking> {
        return this.http.post<Booking>(`${this.apiUrl}/bookings`, request);
    }

    updateBookingStatus(bookingId: number, status: BookingStatus): Observable<Booking> {
        return this.http.patch<Booking>(`${this.apiUrl}/bookings/${bookingId}/status`, { status });
    }

    getHomerBookings(): Observable<Booking[]> {
        return this.http.get<Booking[]>(`${this.apiUrl}/bookings/homer`);
    }

    getCleanerBookings(): Observable<Booking[]> {
        return this.http.get<Booking[]>(`${this.apiUrl}/bookings/cleaner`);
    }
}
