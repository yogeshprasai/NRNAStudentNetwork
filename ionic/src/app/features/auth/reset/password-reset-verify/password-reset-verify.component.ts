import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Validation_Password_Reset_Verify} from "../../../../shared/validation";
import {AuthService} from "../../../../shared/service/auth.service";
import {ToastController} from "@ionic/angular";
import {ActivatedRoute, Router} from "@angular/router";
import {NrnaRoutes} from "../../../../shared/service/constant";

@Component({
  selector: 'app-password-reset-verify',
  templateUrl: './password-reset-verify.component.html',
  styleUrls: ['./password-reset-verify.component.css']
})
export class PasswordResetVerifyComponent implements OnInit {

  public userEmail: string = "";
  public showPassword: boolean = false;
  public invalidToken: boolean = false;
  public passwordResetFailed: boolean = false;
  public errorSendingNewToken: boolean = false;
  public successSendingNewToken: boolean = false;
  public passwordResetSuccessful: boolean = false;
  public Validation_Password_Reset_Verify = Validation_Password_Reset_Verify;
  public passwordResetVerifyForm: FormGroup = new FormGroup({});

  constructor(private activiatedRoute: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private authService: AuthService,
              private toastCtrl: ToastController) { }

  ngOnInit() {
    this.userEmail = this.activiatedRoute.snapshot.params['email'];
    this.passwordResetVerifyForm = this.formBuilder.group({
      token: ['', Validators.compose([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(6)
      ])],
      password: ['', Validators.compose([
        Validators.required,
        Validators.minLength(6),
      ])],
    });
  }

  resendToken(){
    this.authService.sendEmailAndToken(this.userEmail).subscribe(res => {
      if(res.message === "Email Exist") {
        this.successSendingNewToken = true;
      }else {
        this.errorSendingNewToken = true;
      }
    })
  }

  verifyAndPasswordReset(passwordResetVerifyForm: FormGroup){
    this.passwordResetVerifyForm.get('token')?.markAsTouched();
    this.passwordResetVerifyForm.get('password')?.markAsTouched();
    if(this.passwordResetVerifyForm.valid){
      const token = this.passwordResetVerifyForm.get('token')?.value;
      const password = this.passwordResetVerifyForm.get('password')?.value;
      this.authService.passwordResetWithToken(this.userEmail, token, password).subscribe(res => {
        if(res.message === "Success"){
          this.passwordResetSuccessful = true;
        }else if(res.message === "Invalid Token"){
          this.invalidToken = true;
        }
        else{
          this.passwordResetFailed = true;
        }
      });
    }
  }

  private resetButtons(){
    this.invalidToken = false
    this.passwordResetFailed = false;
    this.errorSendingNewToken = false;
    this.successSendingNewToken = false;
    this.passwordResetSuccessful = false;
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  public passwordResetFailedButtons = [
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.resetButtons();
      },
    }
  ];

  public passwordResetSuccessfulButtons = [
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.resetButtons();
        this.navigateToLogin();
      }
    }
  ];

  navigateToLogin(){
    this.passwordResetVerifyForm.reset();
    this.router.navigate(['../../sign-in', {data: {passwordResetSuccessful: "password-reset-success"}}], { relativeTo: this.activiatedRoute });
  }

}
