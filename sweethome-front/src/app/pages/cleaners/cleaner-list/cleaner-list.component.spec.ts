import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';

import { CleanerListComponent } from './cleaner-list.component';
import { CleanerService } from '../../../services/cleaner/cleaner.service';
import { BookingService } from '../../../services/booking/booking.service';
import { CleanerSearchResponseDto } from '../../../models/cleaner-search-response';

describe('CleanerListComponent', () => {
  let component: CleanerListComponent;
  let fixture: ComponentFixture<CleanerListComponent>;
  let cleanerService: jasmine.SpyObj<CleanerService>;
  let bookingService: jasmine.SpyObj<BookingService>;

  beforeEach(async () => {
    const spyCleaner = jasmine.createSpyObj('CleanerService', ['searchGeo', 'getCleaners']);
    spyCleaner.getCleaners.and.returnValue(of([]));
    spyCleaner.searchGeo.and.returnValue(of([{
      id: 1,
      firstName: 'Jean',
      lastName: 'Dupont',
      headline: 'Expert',
      bio: '...',
      pricePerSqm: 10,
      calculatedTotalPrice: 500,
      averageRating: 4.8,
      reviewCount: 10
    }]));

    const spyBooking = jasmine.createSpyObj('BookingService', ['createBookingRequest']);
    spyBooking.createBookingRequest.and.returnValue(of({ id: 1 }));

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterModule.forRoot([]), ReactiveFormsModule, CleanerListComponent],
      providers: [
        { provide: CleanerService, useValue: spyCleaner },
        { provide: BookingService, useValue: spyBooking }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CleanerListComponent);
    component = fixture.componentInstance;
    cleanerService = TestBed.inject(CleanerService) as jasmine.SpyObj<CleanerService>;
    bookingService = TestBed.inject(BookingService) as jasmine.SpyObj<BookingService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call searchGeo on valid form submission', () => {
    component.searchForm.patchValue({
      city: 'Paris',
      surface: 50,
      date: '2026-06-01',
      startTime: '10:00',
      endTime: '12:00'
    });

    component.onSearch();

    expect(cleanerService.searchGeo).toHaveBeenCalledWith(
      48.8566, 2.3522, 50, '2026-06-01', '10:00', '12:00'
    );
    expect(component.cleaners.length).toBe(1);
  });

  it('should open modal and confirm booking', fakeAsync(() => {
    component.searchForm.patchValue({
      city: 'Paris',
      surface: 50,
      date: '2026-06-01',
      startTime: '10:00',
      endTime: '12:00'
    });

    const mockCleaner: CleanerSearchResponseDto = {
      id: 2, firstName: 'Test', lastName: 'Name', headline: '', bio: '',
      pricePerSqm: 10, calculatedTotalPrice: 500, averageRating: 5, reviewCount: 1
    };

    component.openBookingModal(mockCleaner);
    expect(component.isModalOpen).toBeTrue();
    expect(component.selectedCleaner).toEqual(mockCleaner);
    expect(component.bookingSuccess).toBeFalse();

    component.confirmBooking();
    expect(bookingService.createBookingRequest).toHaveBeenCalledWith({
      cleanerId: 2,
      date: '2026-06-01',
      startTime: '10:00',
      endTime: '12:00',
      surface: 50
    });

    // Check intermediate state
    expect(component.bookingSuccess).toBeTrue();
    expect(component.isModalOpen).toBeTrue();

    // Fast-forward 2 seconds
    tick(2000);

    // Check final state
    expect(component.isModalOpen).toBeFalse();
  }));
});
