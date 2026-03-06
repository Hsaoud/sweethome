import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';

import { CleanerDetailComponent } from './cleaner-detail.component';

describe('CleanerDetailComponent', () => {
  let component: CleanerDetailComponent;
  let fixture: ComponentFixture<CleanerDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterModule.forRoot([]), CleanerDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CleanerDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

