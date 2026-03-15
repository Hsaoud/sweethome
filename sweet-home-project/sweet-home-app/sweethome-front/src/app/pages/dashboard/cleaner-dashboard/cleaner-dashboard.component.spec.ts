import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';
import { of } from 'rxjs';

import { CleanerDashboardComponent } from './cleaner-dashboard.component';
import { CleanerDashboardService } from '../../../services/cleaner/cleaner-dashboard.service';
import { BookingService } from '../../../services/booking/booking.service';

describe('CleanerDashboardComponent', () => {
    let component: CleanerDashboardComponent;
    let fixture: ComponentFixture<CleanerDashboardComponent>;
    let dashboardService: jasmine.SpyObj<CleanerDashboardService>;
    let bookingService: jasmine.SpyObj<BookingService>;

    beforeEach(async () => {
        const spyDashboard = jasmine.createSpyObj('CleanerDashboardService', ['getDashboard']);
        spyDashboard.getDashboard.and.returnValue(of({
            user: { firstName: 'Marie', pricePerSqm: 15, available: true },
            averageRating: 4.5,
            reviewCount: 10,
            reviews: []
        }));

        const spyBooking = jasmine.createSpyObj('BookingService', ['getCleanerBookings', 'updateBookingStatus']);
        spyBooking.getCleanerBookings.and.returnValue(of([
            { id: 1, homer: { firstName: 'Homer', lastName: 'Simpson' }, date: '2026-06-01', startTime: '10:00', endTime: '12:00', surface: 50, totalPrice: 500, status: 'PENDING' }
        ]));
        spyBooking.updateBookingStatus.and.returnValue(of(
            { id: 1, homer: { firstName: 'Homer', lastName: 'Simpson' }, date: '2026-06-01', startTime: '10:00', endTime: '12:00', surface: 50, totalPrice: 500, status: 'ACCEPTED' }
        ));

        await TestBed.configureTestingModule({
            imports: [HttpClientTestingModule, RouterModule.forRoot([]), CleanerDashboardComponent],
            providers: [
                { provide: CleanerDashboardService, useValue: spyDashboard },
                { provide: BookingService, useValue: spyBooking }
            ]
        })
            .compileComponents();

        fixture = TestBed.createComponent(CleanerDashboardComponent);
        component = fixture.componentInstance;
        dashboardService = TestBed.inject(CleanerDashboardService) as jasmine.SpyObj<CleanerDashboardService>;
        bookingService = TestBed.inject(BookingService) as jasmine.SpyObj<BookingService>;
        fixture.detectChanges();
    });

    it('should create and load bookings', () => {
        expect(component).toBeTruthy();
        expect(bookingService.getCleanerBookings).toHaveBeenCalled();
        expect(component.bookings.length).toBe(1);
    });

    it('should accept booking and update status', () => {
        component.updateBookingStatus(1, 'ACCEPTED');
        expect(bookingService.updateBookingStatus).toHaveBeenCalledWith(1, 'ACCEPTED');

        const updatedBooking = component.bookings.find(b => b.id === 1);
        expect(updatedBooking?.status).toBe('ACCEPTED');
    });
});
