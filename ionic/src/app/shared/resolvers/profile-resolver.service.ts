import { Injectable } from '@angular/core';
import {Resolve, Router} from '@angular/router';
import {ProfileAddressService} from "../service/profile-address.service";
import {catchError, concatMap} from "rxjs/operators";
import {EMPTY, of} from "rxjs";
import {AuthService} from "../service/auth.service";

@Injectable({
  providedIn: 'root'
})
export class ProfileResolverService implements Resolve<any>{

  constructor(private router: Router, private authService: AuthService, private profileAddressService: ProfileAddressService) { }

  resolve(){
      return this.profileAddressService.getSession().pipe(concatMap(() => {
          return this.profileAddressService.getUserProfile().pipe(catchError (() => {
              this.authService.removeUserAndToken();
              this.authService.login$.next(false);
              this.router.navigate(['auth/sign-in']);
              return of(null);
          }));
      }),
      catchError(() => {
          this.authService.removeUserAndToken();
          this.authService.login$.next(false);
          this.router.navigate(['auth/sign-in']);
          return of(null);
      }));
  }
}
