import { Injectable } from '@angular/core';
import { User } from 'src/app/shared/interface/user';

const AUTH_TOKEN = 'auth-token';
const AUTH_ID = 'auth-id';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {
  constructor() { }

  removeTokenAndUserFromLocalStorage(): void {
    window.localStorage.clear();
  }

  public saveTokenToLocalStorage(token: string): void {
    window.localStorage.removeItem(AUTH_TOKEN);
    window.localStorage.setItem(AUTH_TOKEN, token);
  }

  public getTokenFromLocalStorage(): string | null {
    return window.localStorage.getItem(AUTH_TOKEN);
  }

  public saveUserToLocalStorage(user: User): void {
    window.localStorage.removeItem(AUTH_ID);
    window.localStorage.setItem(AUTH_ID, JSON.stringify(user));
  }

  public getUserFromLocalStorage(): User {
    const user = window.localStorage.getItem(AUTH_ID);
    if (user) {
      return JSON.parse(user);
    }
    return {id: 0};
  }
}
