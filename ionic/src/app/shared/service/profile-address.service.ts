import { Injectable } from '@angular/core';
import {Profile} from "../model/profile";
import {environment} from "../../../environments/environment";
import {catchError, Observable, of, tap, throwError} from "rxjs";
import {ApiService} from "../../core/services/api.service";
import {LocalStorageService} from "../../core/services/local-storage.service";
import { Address } from '../model/address';
import {HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProfileAddressService {

  constructor(
    private apiService: ApiService,
    private localStorageService: LocalStorageService
  ) {}

    getSession(){
         //return throwError("hello");
        return this.apiService.get(environment.server_url + "/api/auth/getSession").pipe(
            catchError(err => {
                throw err;
            })
        )
    }

  updateProfile(profile: Profile){
    console.log(this.localStorageService.getUserFromLocalStorage());
    return this.apiService.post(environment.server_url + "/api/user/profile", profile).pipe(
        tap( response => {
          console.log("Update Profile Data: ", response)
        }),
        catchError(err => {
          throw err;
        })
    );
  }

  getUserProfile(): Observable<any> {
      //return of(new Error("hello"));
    return this.apiService.get(environment.server_url + "/api/user/profile").pipe(
        tap( response => {
          console.log("Get User Profile: ", response)
        }),
        catchError(err => {
          throw err;
        }));
  }

  getUserAddress(): Observable<Address>{
    return this.apiService.get(environment.server_url + "/api/user/address").pipe(
        tap( response => {
          console.log("Get User Address: ", response)
        }),
        catchError(err => {
          throw err;
        }));
  }

  saveOrUpdateAddress(userAddress: Address){
    return this.apiService.post(environment.server_url + "/api/user/address", userAddress).pipe(
        tap( response => {
          console.log("Saving Address Successful: ", response)
        }),
        catchError(err => {
          throw err;
        }));
  }


    saveProfilePicture(base64Image: any) {
        console.log("Profile Picture Saving");
        //const base64ImageFixed = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAADMElEQVR4nOzVwQnAIBQFQYXff81RUkQCOyDj1YOPnbXWPmeTRef+/3O/OyBjzh3CD95BfqICMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMO0TAAD//2Anhf4QtqobAAAAAElFTkSuQmCC\n";
        let params = new HttpParams()
            .set('image', base64Image)
        return this.apiService.post(environment.server_url + "/api/user/saveOrUpdateProfilePic", params).pipe(tap(res => {
            console.log(res);
        }),
            catchError(err => {
            throw err;
        }));
    }

    deleteProfilePicture(): Observable<any>{
      return this.apiService.put(environment.server_url + "/api/user/deleteProfilePicture").pipe(tap(res => {
        console.log(res);
      }),
      catchError(err => {
        throw err;
      }));
    }


}
