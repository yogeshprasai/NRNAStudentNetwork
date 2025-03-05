import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import {catchError, filter, map, Observable, of, tap, throwError} from 'rxjs';
import {UsersService} from "../service/users.service";

@Injectable({
    providedIn: 'root'
})
export class VolunteerResolverService implements Resolve<any>{

    constructor(private usersService: UsersService) {}

    resolve(): Observable<any>{
        return this.usersService.getAllVolunteers().pipe(
            filter(users => users),
            catchError(err => {
                //not regular practice but to show error on page
                return of(null);
            })
        );
    }
}
