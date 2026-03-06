import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';

import { RegisterCleanerComponent } from './register-cleaner.component';

describe('RegisterCleanerComponent', () => {
  let component: RegisterCleanerComponent;
  let fixture: ComponentFixture<RegisterCleanerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterModule.forRoot([]), RegisterCleanerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegisterCleanerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

