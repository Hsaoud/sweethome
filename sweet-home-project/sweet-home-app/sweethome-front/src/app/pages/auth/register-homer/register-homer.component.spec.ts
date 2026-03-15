import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';

import { RegisterHomerComponent } from './register-homer.component';

describe('RegisterHomerComponent', () => {
  let component: RegisterHomerComponent;
  let fixture: ComponentFixture<RegisterHomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterModule.forRoot([]), RegisterHomerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegisterHomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

