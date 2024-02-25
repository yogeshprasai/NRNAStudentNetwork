import { Injectable } from '@angular/core';
import { User } from '../interface/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public userId: string = "";
  public loggedInUser: User = {} as User;
  public isUserLoggedIn: boolean = false;
  public loggedInUserEmail: string = ""; 

  constructor(
  ) {}

  ngOnInit(){


    
  }

  getUser(): User {
    return {firstName: "Yogesh", lastName: "Prasai", email: "yogeshprasai@hotmail.com"};
  }

  login(email: string, password: string) {
    return null;
  }

  setLoggedInUserEmail(loggedInUserEmail: string){
    this.loggedInUserEmail = loggedInUserEmail;
  }

  getLoggedInUserEmail(){
    return this.loggedInUserEmail;
  }

  setLoggedInUser(){
    const user: User = this.getUser();
    //alert(user);
    if(user != null){
      console.log(user);
      this.loggedInUser = user;
      this.loggedInUserEmail = user.email;
      return this.loggedInUser;
    }else{
      this.loggedInUser = this.resetUser(this.loggedInUser);
      return this.loggedInUser;
    }
  }

  async calculateAndSetTypeOfUser(email: string){
    //const val = await this.studentService.calculateUserAndSet(email);
    //return val;
  }

  public resetUser(user: User){
    user.firstName = "";
    user.lastName = "";
    user.email = "";

    return user;
  }

  public getTypeOfUser(){
    //return this.studentService.getTypeOfUser();
  }

  public setTypeOfUser(typeOfUser: string){
    //this.studentService.setTypeOfUser(typeOfUser);
  }

  public signup(email: string, password: string) {
    
  }

  public resetPassword(email: string) {
    //return this.afAuth.sendPasswordResetEmail(email);
  }

  public logout() {
    //return this.afAuth.signOut();
  }


}
