import { TestBed } from '@angular/core/testing';

import { LoginResolverService } from './login-resolver.service';

describe('LoginResolversService', () => {
  let service: LoginResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoginResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
