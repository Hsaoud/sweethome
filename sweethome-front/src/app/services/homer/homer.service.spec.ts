import { TestBed } from '@angular/core/testing';

import { HomerService } from './homer.service';

describe('HomerService', () => {
  let service: HomerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HomerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
