import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertController, LoadingController } from '@ionic/angular';
import { Validation_Login } from 'src/app/shared/validation';
import { loginResponse } from 'src/app/shared/model/loginResponse';
import { AuthService } from 'src/app/shared/service/auth.service';
import { LoadingService } from 'src/app/shared/service/loading.service';
import { User } from 'src/app/shared/interface/user';
import { NrnaRoutes } from 'src/app/shared/service/constant';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent  implements OnInit {

  public loginForm: FormGroup;
  validationLogin = Validation_Login;
  showSpinner: boolean = false;
  isLoggedIn: boolean = false;
  showFailMessage: boolean = false;

  constructor(private router: Router, private formBuilder: FormBuilder, private loadingService: LoadingService,
    private loadingCtrl: LoadingController, private alertController: AlertController, private authService: AuthService) { 
      this.loginForm = this.formBuilder.group({
        email: ['', Validators.compose([
          Validators.required,
          Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"),
        ])],
        password: ['', Validators.compose([
          Validators.required,
        ])]
      });
    }

  ngOnInit() {
    
  }

  ionViewWillEnter(){
    if(this.authService.isLoggedIn){
      this.router.navigate(['user-dashboard/profile']);
    }
    console.log('Reseting Login Form');
    //this.loginForm.get('email').reset();
    //this.loginForm.get('password').reset();
  }

  submitCredentials(loginForm: FormGroup): void {
    if (!loginForm.valid) {
      console.log('Form is not valid yet, current value:', loginForm.value);
    } else {
      this.showSpinner = true;
      const email = this.loginForm.get('email')?.value;
      const password = this.loginForm.get('password')?.value;
      this.authService.login(email, password).subscribe((loginResponse: loginResponse) => {
        this.showSpinner = false;
        if(loginResponse.token){
          this.isLoggedIn = true;
          const user: User = {
            email: email
          }
          this.authService.loggedInUser = user;
          this.router.navigate([NrnaRoutes.Profile]);
        }else{
          this.isLoggedIn = false;
          this.showFailMessage = true;
        }
      });
    }
  }

  public LoginFailButtons = [
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.isLoggedIn = false;
        this.showFailMessage = false;
      },
    },
  ];

}
