import {ActivatedRouteSnapshot, MaybeAsync, Resolve, RouterStateSnapshot} from "@angular/router";
import {ProfileAddressService} from "../service/profile-address.service";
import {Injectable} from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class AddressResolverService implements Resolve<any>{

    constructor(private profileAddressService: ProfileAddressService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<any> {
        return this.profileAddressService.getUserAddress();
    }
}