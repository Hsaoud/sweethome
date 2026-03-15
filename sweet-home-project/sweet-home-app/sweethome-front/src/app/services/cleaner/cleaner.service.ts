import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../models/user';
import { CleanerSearchResponseDto } from '../../models/cleaner-search-response';

@Injectable({
  providedIn: 'root'
})
export class CleanerService {
  private apiUrl = 'http://localhost:8080/api/cleaners';

  constructor(private http: HttpClient) { }

  getCleaners(city?: string, categoryId?: number): Observable<User[]> {
    let params = new HttpParams();
    if (city) params = params.set('city', city);
    if (categoryId) params = params.set('categoryId', categoryId);

    return this.http.get<User[]>(this.apiUrl, { params });
  }

  getCleanerProfile(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  searchGeo(lat: number, lng: number, surface?: number, date?: string, startTime?: string, endTime?: string): Observable<CleanerSearchResponseDto[]> {
    let params = new HttpParams()
      .set('lat', lat.toString())
      .set('lng', lng.toString());

    if (surface) params = params.set('surface', surface.toString());
    if (date) params = params.set('date', date);
    if (startTime) params = params.set('startTime', startTime);
    if (endTime) params = params.set('endTime', endTime);

    return this.http.get<CleanerSearchResponseDto[]>(`${this.apiUrl}/searchGeo`, { params });
  }
}
