import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';
import { of } from 'rxjs';

import { HomerDashboardComponent } from './homer-dashboard.component';
import { HomerService } from '../../../services/homer/homer.service';
import { BookingService } from '../../../services/booking/booking.service';

describe('HomerDashboardComponent', () => {
  let component: HomerDashboardComponent;
  let fixture: ComponentFixture<HomerDashboardComponent>;
  let homerService: jasmine.SpyObj<HomerService>;
  let bookingService: jasmine.SpyObj<BookingService>;

  beforeEach(async () => {
    const spyHomer = jasmine.createSpyObj('HomerService', ['getDashboard']);
    spyHomer.getDashboard.and.returnValue(of({
      user: { firstName: 'Homer', city: 'Springfield' },
      reviewsGiven: [],
      reviewsReceived: []
    }));

    const spyBooking = jasmine.createSpyObj('BookingService', ['getHomerBookings']);
    spyBooking.getHomerBookings.and.returnValue(of([
      { id: 1, cleaner: { firstName: 'Marie', lastName: 'Curie' }, date: '2026-06-01', startTime: '10:00', endTime: '12:00', surface: 50, totalPrice: 500, status: 'PENDING' }
    ]));

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterModule.forRoot([]), HomerDashboardComponent],
      providers: [
        { provide: HomerService, useValue: spyHomer },
        { provide: BookingService, useValue: spyBooking }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(HomerDashboardComponent);
    component = fixture.componentInstance;
    homerService = TestBed.inject(HomerService) as jasmine.SpyObj<HomerService>;
    bookingService = TestBed.inject(BookingService) as jasmine.SpyObj<BookingService>;
    fixture.detectChanges();
  });

  it('should create and load bookings', () => {
    expect(component).toBeTruthy();
    expect(bookingService.getHomerBookings).toHaveBeenCalled();
    expect(component.bookings.length).toBe(1);
    expect(component.bookings[0].status).toBe('PENDING');
  });
});
