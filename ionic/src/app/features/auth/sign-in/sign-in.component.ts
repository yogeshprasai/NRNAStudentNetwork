import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { AlertController, LoadingController } from '@ionic/angular';
import { Validation_Login } from 'src/app/shared/validation';
import { loginResponse } from 'src/app/shared/model/loginResponse';
import { AuthService } from 'src/app/shared/service/auth.service';
import { LoadingService } from 'src/app/shared/service/loading.service';
import { User } from 'src/app/shared/interface/user';
import { NrnaRoutes } from 'src/app/shared/service/constant';
import { NavigationService } from 'src/app/shared/service/navigation.service';
import {ProfileAddressService} from "../../../shared/service/profile-address.service";

@Component({
  selector: 'nrna-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent  implements OnInit {

  public loginForm: FormGroup;
  validationLogin = Validation_Login;
  showSpinner: boolean = false;
  isLoggedIn: boolean = false;
  showPassword: boolean = false;
  showFailMessage: boolean = false;

  constructor(private router: Router, private activiatedRoute: ActivatedRoute, private formBuilder: FormBuilder,
              private loadingService: LoadingService, private loadingCtrl: LoadingController,
              private alertController: AlertController, private authService: AuthService,
              private navigationService: NavigationService, private profileAddressService: ProfileAddressService) {
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
    const passwordResetSuccess = this.activiatedRoute.snapshot.params['data'];
    if(passwordResetSuccess){

    }
  }

  ionViewWillEnter(){
    if(this.authService.isLoggedIn){
      this.router.navigate(['user-dashboard/profile']);
    }
  }

  submitCredentials(loginForm: FormGroup): void {
    loginForm.get('email')?.markAsTouched();
    loginForm.get('password')?.markAsTouched();
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
            id: loginResponse.id,
          }
          this.authService.loggedInUser = user;
          this.authService.token = loginResponse.token;
          this.router.navigate([NrnaRoutes.Profile]);
          this.navigationService.reArrangeMenuItem();
        }
      }, error => {
        this.showSpinner = false;
        this.isLoggedIn = false;
        this.showFailMessage = true;
      });
    }
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  resetButtons() {
    this.showFailMessage = false;
  }

  public LoginFailButtons = [
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.isLoggedIn = false;
        this.showFailMessage = false;
        this.resetButtons();
      },
    },
  ];

}
