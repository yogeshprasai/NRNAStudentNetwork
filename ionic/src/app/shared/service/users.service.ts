import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";
import {ApiService} from "../../core/services/api.service";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private apiService: ApiService) { }

    getAllApplyForVolunteerRequest(): Observable<any>{
        return this.apiService.get(environment.server_url + "/api/users/getAllApplyForVolunteerRequest");
    }

    getAllVolunteers(): Observable<any>{
        return this.apiService.get(environment.server_url + "/api/users/getAllVolunteers");
    }

    getAllStudents(): Observable<any>{
        return this.apiService.get(environment.server_url + "/api/users/getAllStudents");
    }
}
