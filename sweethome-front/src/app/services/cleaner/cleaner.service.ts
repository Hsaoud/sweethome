import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../models/user';

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
}
