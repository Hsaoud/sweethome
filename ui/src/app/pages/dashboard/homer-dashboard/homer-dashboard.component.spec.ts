import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomerDashboardComponent } from './homer-dashboard.component';

describe('HomerDashboardComponent', () => {
  let component: HomerDashboardComponent;
  let fixture: ComponentFixture<HomerDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomerDashboardComponent]
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
