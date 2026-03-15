import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { PLATFORM_ID } from '@angular/core';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        AuthService,
        { provide: Router, useValue: routerSpy },
        { provide: PLATFORM_ID, useValue: 'browser' }
      ]
    });

    // Clear localStorage for tests
    localStorage.clear();

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('login should send a POST request and save user', () => {
    const mockUser = { id: 1, email: 'test@test.com', firstName: 'Test', lastName: 'User', role: 'HOMER' };
    const credentials = { email: 'test@test.com', password: 'password' };

    service.login(credentials).subscribe((user) => {
      expect(user).toEqual(mockUser as any);
      expect(service.currentUser()).toEqual(mockUser as any);
      expect(localStorage.getItem('user')).toEqual(JSON.stringify(mockUser));
    });

    const req = httpMock.expectOne('http://localhost:8080/api/auth/login');
    expect(req.request.method).toBe('POST');
    req.flush(mockUser);
  });

  it('registerHomer should send a POST request and save user', () => {
    const mockUser = { id: 2, email: 'homer@test.com', firstName: 'Homer', lastName: 'Test', role: 'HOMER' };

    service.registerHomer({ email: 'homer@test.com' }).subscribe((user) => {
      expect(user).toEqual(mockUser as any);
      expect(service.currentUser()).toEqual(mockUser as any);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/auth/register/homer');
    expect(req.request.method).toBe('POST');
    req.flush(mockUser);
  });

  it('registerCleaner should send a POST request and save user', () => {
    const mockUser = { id: 3, email: 'cleaner@test.com', firstName: 'Cleaner', lastName: 'Test', role: 'CLEANER' };

    service.registerCleaner({ email: 'cleaner@test.com' }).subscribe((user) => {
      expect(user).toEqual(mockUser as any);
      expect(service.currentUser()).toEqual(mockUser as any);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/auth/register/cleaner');
    expect(req.request.method).toBe('POST');
    req.flush(mockUser);
  });

  it('logout should clear user state and redirect', () => {
    service.currentUser.set({ id: 1, email: 'test@test.com', firstName: 'Test', lastName: 'User', role: 'HOMER' } as any);
    localStorage.setItem('user', JSON.stringify({ id: 1 }));

    service.logout();

    const req = httpMock.expectOne('http://localhost:8080/api/auth/logout');
    expect(req.request.method).toBe('POST');
    req.flush({}); // Mock successful logout response

    expect(service.currentUser()).toBeNull();
    expect(localStorage.getItem('user')).toBeNull();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  });

  it('isLoggedIn should return true if currentUser is set', () => {
    service.currentUser.set(null as any);
    expect(service.isLoggedIn).toBeFalse();

    service.currentUser.set({ id: 1, email: 'test@test.com', firstName: 'Test', lastName: 'User', role: 'HOMER' } as any);

    expect(service.isLoggedIn).toBeTrue();
  });
});
