import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { UserProfile } from '../model/constants';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private userProfile: UserProfile = {
    email : "",
    fullName: ""
  };

  constructor(
    private authService: AuthService
  ) {}

  // async getUserProfile(): Promise<Observable<UserProfile>> {
  //   const user: firebase.User = await this.authService.getUser();
  //   this.currentUser = user;
  //   this.userProfile = this.firestore.doc(`USER/${user.uid}`);
  //   return this.userProfile.valueChanges();
  // }

  updateName(fullName: string) {
    //return this.userProfile.update({ fullName });
  }

  updateEmail(newEmail: string, password: string) {
    // const credential: firebase.auth.AuthCredential = firebase.auth.EmailAuthProvider.credential(
    //   this.currentUser.email,
    //   password
    // );
    try {
      //await this.currentUser.reauthenticateWithCredential(credential);
      //await this.currentUser.updateEmail(newEmail);
      //return this.userProfile.update({ email: newEmail });
    } catch (error) {
      console.error(error);
    }
  }

  async updatePassword(
    newPassword: string,
    oldPassword: string
  ) {
    // const credential: firebase.auth.AuthCredential = firebase.auth.EmailAuthProvider.credential(
    //   this.currentUser.email,
    //   oldPassword
    // );
    try {
      // await this.currentUser.reauthenticateWithCredential(credential);
      // return this.currentUser.updatePassword(newPassword);
    } catch (error) {
      console.error(error);
    }
  }
}
