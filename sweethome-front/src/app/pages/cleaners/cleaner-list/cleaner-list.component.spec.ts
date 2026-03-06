import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';

import { CleanerListComponent } from './cleaner-list.component';

describe('CleanerListComponent', () => {
  let component: CleanerListComponent;
  let fixture: ComponentFixture<CleanerListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterModule.forRoot([]), CleanerListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CleanerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

