import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';
import { Component } from '@angular/core';

@Component({ selector: 'app-navbar', template: '' })
class MockNavbarComponent { }

@Component({ selector: 'app-footer', template: '' })
class MockFooterComponent { }

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent, HttpClientTestingModule, RouterModule.forRoot([])],
    }).overrideComponent(AppComponent, {
      remove: { imports: [/* NavbarComponent, FooterComponent */] },
      add: { imports: [/* MockNavbarComponent, MockFooterComponent */] }
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have the 'ui' title`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('ui');
  });
});
