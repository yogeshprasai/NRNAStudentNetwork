import {ActivatedRouteSnapshot, MaybeAsync, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {ProfileAddressService} from "../service/profile-address.service";
import {Injectable} from "@angular/core";
import {catchError} from "rxjs/operators";
import {of} from "rxjs";
import {AuthService} from "../service/auth.service";

@Injectable({
    providedIn: 'root'
})
export class AddressResolverService implements Resolve<any>{

    constructor(private router: Router, private authService: AuthService, private profileAddressService: ProfileAddressService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<any> {
        return this.profileAddressService.getUserAddress().pipe(catchError (err => {
            if(err.status === 401){
                this.authService.removeUserAndToken();
                this.router.navigate(['auth/sign-in']);
            }
            return of(null);
        }));
    }
}