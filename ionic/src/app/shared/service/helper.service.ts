import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {Observable, catchError, map, filter} from "rxjs";
import {ApiService} from "../../core/services/api.service";
import {Profile} from "../model/profile";

@Injectable({
  providedIn: 'root'
})
export class HelperService {

  private listOfMembers: any = [];

  constructor(private apiService: ApiService) { }

    getAllHelpers(): Observable<any>{
    return this.apiService.get(environment.server_url + "/api/helper/getAllHelpers").pipe(
        catchError(err => {
          throw new Error("Error while retrieving Profile. " + err);
        }));
  }
}
