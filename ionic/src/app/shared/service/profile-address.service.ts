import { Injectable } from '@angular/core';
import {Profile} from "../model/profile";
import {environment} from "../../../environments/environment";
import {catchError, Observable, of, tap} from "rxjs";
import {ApiService} from "../../core/services/api.service";
import {LocalStorageService} from "../../core/services/local-storage.service";
import { Address } from '../model/address';

@Injectable({
  providedIn: 'root'
})
export class ProfileAddressService {

  constructor(
    private apiService: ApiService,
    private localStorageService: LocalStorageService
  ) {}

  updateProfile(profile: Profile){
    console.log(this.localStorageService.getUserFromLocalStorage());
    return this.apiService.post(environment.server_url + "/api/user/profile", profile, ).pipe(
        catchError(err => {
          throw new Error("Error while updating Profile. " + err);
        })
    );
  }

  getUserProfile(): Observable<any> {
    return this.apiService.get(environment.server_url + "/api/user/profile").pipe(tap( response => {
          console.log("Get Profile Data: " + response)
        }), catchError(err => {
            throw new Error("Error while retrieving Profile. " + err);
    }));
  }

  getUserAddress(): Observable<Address>{
    return this.apiService.get(environment.server_url + "/api/user/address").pipe(catchError(err => {
      throw new Error("Error while retrieving Address" + err);
    }));
  }

  saveOrUpdateAddress(userAddress: Address){
    return this.apiService.post(environment.server_url + "/api/user/address", userAddress).pipe(catchError(err => {
      throw new Error("Error updating Address");
    }))
  }



}
