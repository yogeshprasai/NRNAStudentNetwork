import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import { Observable} from "rxjs";
import {ApiService} from "../../core/services/api.service";
import { Address } from '../model/address';
import {HttpParams} from "@angular/common/http";
import {UserProfile} from "../model/user-profile";

@Injectable({
  providedIn: 'root'
})
export class ProfileAddressService {

  constructor(
    private apiService: ApiService
  ) {}

    getSession(){
        return this.apiService.get(environment.server_url + "/api/auth/getSession");
    }

    updateProfile(userProfile: UserProfile){
      return this.apiService.post(environment.server_url + "/api/user/profile", userProfile);
    }

    profileApprovalByAdmin(userProfile: UserProfile){
        return this.apiService.post(environment.server_url + "/api/user/profileApprovalByAdmin", userProfile);
    }

    getUserProfile(): Observable<any> {
      return this.apiService.get(environment.server_url + "/api/user/profile");
    }

    getUserAddress(): Observable<Address>{
      return this.apiService.get(environment.server_url + "/api/user/address");
    }

    saveOrUpdateAddress(userAddress: Address){
      return this.apiService.post(environment.server_url + "/api/user/address", userAddress);
    }

    saveProfilePicture(base64Image: any) {
        let params = new HttpParams()
            .set('image', base64Image)
        return this.apiService.post(environment.server_url + "/api/user/saveOrUpdateProfilePic", params);
    }

    deleteProfilePicture(): Observable<any>{
      return this.apiService.put(environment.server_url + "/api/user/deleteProfilePicture");
    }

}
