import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BookingService } from './booking.service';
import { BookingRequestDto, Booking, BookingStatus } from '../../models/booking.model';

describe('BookingService', () => {
    let service: BookingService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [BookingService]
        });
        service = TestBed.inject(BookingService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should create a booking request', () => {
        const mockRequest: BookingRequestDto = {
            cleanerId: 2,
            date: '2026-06-01',
            startTime: '10:00:00',
            endTime: '12:00:00',
            surface: 50
        };

        const mockResponse: Booking = {
            id: 1,
            homer: {} as any,
            cleaner: {} as any,
            date: '2026-06-01',
            startTime: '10:00:00',
            endTime: '12:00:00',
            surface: 50,
            totalPrice: 750,
            status: 'PENDING'
        };

        service.createBookingRequest(mockRequest).subscribe(response => {
            expect(response).toEqual(mockResponse);
        });

        const req = httpMock.expectOne(`http://localhost:8080/api/bookings`);
        expect(req.request.method).toBe('POST');
        expect(req.request.body).toEqual(mockRequest);
        req.flush(mockResponse);
    });

    it('should update booking status', () => {
        const mockResponse: Booking = {
            id: 1,
            homer: {} as any,
            cleaner: {} as any,
            date: '2026-06-01',
            startTime: '10:00',
            endTime: '12:00',
            surface: 50,
            totalPrice: 750,
            status: 'ACCEPTED'
        };

        service.updateBookingStatus(1, 'ACCEPTED').subscribe(response => {
            expect(response.status).toBe('ACCEPTED');
        });

        const req = httpMock.expectOne(`http://localhost:8080/api/bookings/1/status`);
        expect(req.request.method).toBe('PATCH');
        expect(req.request.body).toEqual({ status: 'ACCEPTED' });
        req.flush(mockResponse);
    });
});
