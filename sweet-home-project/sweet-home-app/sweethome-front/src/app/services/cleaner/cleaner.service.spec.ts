import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CleanerService } from './cleaner.service';
import { CleanerSearchResponseDto } from '../../models/cleaner-search-response';

describe('CleanerService', () => {
    let service: CleanerService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [CleanerService]
        });
        service = TestBed.inject(CleanerService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should search geo cleaners', () => {
        const mockResponse: CleanerSearchResponseDto[] = [
            {
                id: 2,
                firstName: 'Marie',
                lastName: 'Curie',
                headline: 'Great cleaner',
                bio: '...',
                pricePerSqm: 15,
                calculatedTotalPrice: 750,
                averageRating: 5.0,
                reviewCount: 1
            }
        ];

        service.searchGeo(48.8, 2.3, 50, '2026-06-01', '10:00:00', '12:00:00').subscribe(response => {
            expect(response).toEqual(mockResponse);
        });

        const req = httpMock.expectOne(request =>
            request.url === `http://localhost:8080/api/cleaners/searchGeo` &&
            request.params.get('lat') === '48.8' &&
            request.params.get('lng') === '2.3' &&
            request.params.get('surface') === '50' &&
            request.params.get('date') === '2026-06-01' &&
            request.params.get('startTime') === '10:00:00' &&
            request.params.get('endTime') === '12:00:00'
        );
        expect(req.request.method).toBe('GET');
        req.flush(mockResponse);
    });
});
