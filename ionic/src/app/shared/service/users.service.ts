import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {Observable, catchError} from "rxjs";
import {ApiService} from "../../core/services/api.service";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private apiService: ApiService) { }

    getAllVolunteers(): Observable<any>{
        return this.apiService.get(environment.server_url + "/api/users/getAllVolunteers").pipe(
            catchError(err => {
              throw new Error("Error while retrieving Profile. " + err);
            }));
    }

    getAllStudents(): Observable<any>{
        return this.apiService.get(environment.server_url + "/api/users/getAllStudents").pipe(
            catchError(err => {
                throw new Error("Error while retrieving Profile. " + err);
            }));
    }
}
