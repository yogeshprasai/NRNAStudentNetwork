import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { Observable } from 'rxjs';
import { NrnaRoutes } from '../service/constant';

@Injectable({
  providedIn: 'root'
})
export class LoginResolverService implements Resolve<any>{

  constructor(private authService: AuthService, private router: Router) { }

  resolve(route: ActivatedRouteSnapshot): boolean | null{
    if(this.authService.isLoggedIn){
      return true;
    }else{
      //this.router.navigate([NrnaRoutes.Login]);
      return true;
    }
  }
}
