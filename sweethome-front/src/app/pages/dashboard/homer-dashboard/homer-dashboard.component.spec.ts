import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';

import { HomerDashboardComponent } from './homer-dashboard.component';

describe('HomerDashboardComponent', () => {
  let component: HomerDashboardComponent;
  let fixture: ComponentFixture<HomerDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterModule.forRoot([]), HomerDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HomerDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

