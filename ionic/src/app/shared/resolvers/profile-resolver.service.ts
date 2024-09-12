import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import {ProfileAddressService} from "../service/profile-address.service";

@Injectable({
  providedIn: 'root'
})
export class ProfileResolverService implements Resolve<any>{

  constructor(private profileAddressService: ProfileAddressService) { }

  resolve(){
    return this.profileAddressService.getUserProfile();
  }
}
