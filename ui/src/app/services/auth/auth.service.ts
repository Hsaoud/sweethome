import { Injectable, signal, PLATFORM_ID, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { User } from '../../models/user';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8080/api/auth';

    public currentUser = signal<User | null>(null);

    constructor(
        private http: HttpClient,
        private router: Router,
        @Inject(PLATFORM_ID) private platformId: Object
    ) {
        if (isPlatformBrowser(this.platformId)) {
            const stored = localStorage.getItem('user');
            if (stored) {
                this.currentUser.set(JSON.parse(stored));
            }
        }
    }

    login(credentials: any): Observable<User> {
        return this.http.post<User>(`${this.apiUrl}/login`, credentials).pipe(
            tap(user => this.handleAuthSuccess(user))
        );
    }

    registerHomer(data: any): Observable<User> {
        return this.http.post<User>(`${this.apiUrl}/register/homer`, data).pipe(
            tap(user => this.handleAuthSuccess(user))
        );
    }

    registerCleaner(data: any): Observable<User> {
        return this.http.post<User>(`${this.apiUrl}/register/cleaner`, data).pipe(
            tap(user => this.handleAuthSuccess(user))
        );
    }

    logout(): void {
        this.http.post(`${this.apiUrl}/logout`, {}).subscribe(() => {
            this.currentUser.set(null);
            if (isPlatformBrowser(this.platformId)) {
                localStorage.removeItem('user');
            }
            this.router.navigate(['/']);
        });
    }

    private handleAuthSuccess(user: User) {
        this.currentUser.set(user);
        if (isPlatformBrowser(this.platformId)) {
            localStorage.setItem('user', JSON.stringify(user));
        }
    }

    get isLoggedIn() {
        return !!this.currentUser();
    }
}
