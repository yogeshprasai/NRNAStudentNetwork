import { Injectable } from '@angular/core';
import { User } from '../interface/user';
import { ApiService } from 'src/app/core/services/api.service';
import { environment } from 'src/environments/environment';
import {Observable, Subject, catchError, of, tap, map} from 'rxjs';
import { LocalStorageService } from 'src/app/core/services/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public login$ = new Subject<boolean>();

  constructor(
    private apiService: ApiService,
    private localStorageService: LocalStorageService
  ) {}

  public resetUser(user: User){
    user.id = 0;
    return user;
  }

  get isLoggedIn(): boolean{
    if(this.loggedInUser && this.loggedInUser.id){
      return true;
    }else {
      return false;
    }
  }

  get isAdmin(): boolean{
    return true;
  }

  get loggedInUser(): User {
    return this.localStorageService.getUserFromLocalStorage();
  }

  set loggedInUser(user: User){
    this.localStorageService.saveUserToLocalStorage(user);
    this.login$.next(this.isLoggedIn);
  }

  get token(): string | null{
    return this.localStorageService.getTokenFromLocalStorage();
  }

  set token(token: string){
    this.localStorageService.saveTokenToLocalStorage(token);
  }

  removeUserAndToken(): void{
    this.localStorageService.removeTokenAndUserFromLocalStorage();
  }

  public signup(email: string, password: string): Observable<any> {
    return this.apiService.post(environment.server_url + "/api/auth/signup", {email, password});
  }

  login(email: string, password: string): Observable<any> {
    return this.apiService.post(environment.server_url + "/api/auth/authenticate", {email, password});
  }

  public logout() {
    return this.apiService.post(environment.server_url + '/api/auth/logout');
  }

  public sendTokenEmailToResetPassword(email: string) {
    return this.apiService.post(environment.server_url + '/api/auth/sendTokenEmailToResetPassword', {email});
  }

  public verifyTokenToResetPassword(email: string, token: string) {
    return this.apiService.post(environment.server_url + '/api/auth/verifyTokenToResetPassword',
                                    {email: email, token: token});
  }

  public newPasswordToResetPassword(email: string, password: string) {
    return this.apiService.post(environment.server_url + '/api/auth/newPasswordToResetPassword',
                                    {email: email, password: password});
  }

}
