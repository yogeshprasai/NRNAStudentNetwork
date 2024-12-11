import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import {filter, map, Observable, tap} from 'rxjs';
import {UsersService} from "../service/users.service";

@Injectable({
  providedIn: 'root'
})
export class HelperResolverService implements Resolve<any>{

  constructor(private usersService: UsersService) {}

  resolve(): Observable<any>{
    return this.usersService.getAllUsers().pipe(filter(user => user.firstName !== null &&
      user.lastName != null));
  }
}
