import { TestBed } from '@angular/core/testing';

import { AppModuleService } from './app-module.service';

describe('AppModuleService', () => {
  let service: AppModuleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AppModuleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
