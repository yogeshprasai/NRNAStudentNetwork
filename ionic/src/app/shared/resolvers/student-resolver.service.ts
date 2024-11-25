import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import {UsersService} from "../service/users.service";

@Injectable({
  providedIn: 'root'
})
export class StudentResolverService implements Resolve<any>{

  constructor(private usersService: UsersService) {}

  resolve(): Observable<any>{
    return this.usersService.getAllUsers();
  }
}
