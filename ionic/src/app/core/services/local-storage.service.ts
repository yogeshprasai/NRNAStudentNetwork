import { Injectable } from '@angular/core';
import { User } from 'src/app/shared/interface/user';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {
  constructor() { }

  removeTokenAndUserFromLocalStorage(): void {
    window.localStorage.clear();
  }

  public saveTokenToLocalStorage(token: string): void {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getTokenFromLocalStorage(): string | null {
    return window.localStorage.getItem(TOKEN_KEY);
  }

  public saveUserToLocalStorage(user: User): void {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUserFromLocalStorage(): User {
    const user = window.localStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return {id: 0};
  }
}
