import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {catchError, Observable, of, tap, throwError} from "rxjs";
import {ApiService} from "../../core/services/api.service";
import {LocalStorageService} from "../../core/services/local-storage.service";
import { Address } from '../model/address';
import {HttpParams} from "@angular/common/http";
import {UserProfile} from "../model/user-profile";

@Injectable({
  providedIn: 'root'
})
export class ProfileAddressService {

  constructor(
    private apiService: ApiService,
    private localStorageService: LocalStorageService
  ) {}

    getSession(){
        return this.apiService.get(environment.server_url + "/api/auth/getSession").pipe(
            catchError(err => {
                throw err;
            })
        )
    }

  updateProfile(userProfile: UserProfile){
    console.log(this.localStorageService.getUserFromLocalStorage());
    return this.apiService.post(environment.server_url + "/api/user/profile", userProfile).pipe(
        tap( response => {
          console.log("Update Profile Data: ", response)
        }),
        catchError(err => {
          throw err;
        })
    );
  }

    profileApprovalByAdmin(userProfile: UserProfile){
        console.log(this.localStorageService.getUserFromLocalStorage());
        return this.apiService.post(environment.server_url + "/api/user/profileApprovalByAdmin", userProfile).pipe(
            tap( (userProfile: UserProfile) => {
                console.log("Update Profile Data: ", userProfile)
            }),
            catchError(err => {
                throw err;
            })
        );
    }

  getUserProfile(): Observable<any> {
    return this.apiService.get(environment.server_url + "/api/user/profile").pipe(
        tap( (userProfile: UserProfile) => {
          console.log("Get User Profile: ", userProfile)
        }),
        catchError(err => {
          throw err;
        }));
  }

  getUserAddress(): Observable<Address>{
    return this.apiService.get(environment.server_url + "/api/user/address").pipe(
        tap( (address: Address) => {
          console.log("Get User Address: ", address)
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
