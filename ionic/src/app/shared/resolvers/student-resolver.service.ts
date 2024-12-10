import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import {Observable, of} from 'rxjs';
import {UsersService} from "../service/users.service";
import {AuthService} from "../service/auth.service";

@Injectable({
  providedIn: 'root'
})
export class StudentResolverService implements Resolve<any>{

  constructor(private usersService: UsersService, private authService: AuthService) {}

  resolve(): Observable<any>{
    if(this.authService.isLoggedIn){
      return this.usersService.getAllUsers();
    }
    return of(null);
  }
}
