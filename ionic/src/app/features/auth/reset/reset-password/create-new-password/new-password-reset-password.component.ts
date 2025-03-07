import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/shared/service/auth.service';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'nrna-reset-new-password',
  templateUrl: './new-password-reset-password.component.html',
  styleUrls: ['./new-password-reset-password.component.css']
})
export class NewPasswordResetPasswordComponent implements OnInit {

  public userEmail: string = "";
  public showPassword: boolean = false;
  public passwordResetFailed: boolean = false;
  public passwordResetSuccessful: boolean = false;
  public resetNewPasswordForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder, private authService: AuthService,
              private activiatedRoute: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.userEmail = this.activiatedRoute.snapshot.params['email'];
    this.resetNewPasswordForm = this.formBuilder.group({
          password: ['', Validators.compose([
            Validators.required,
            Validators.minLength(6),
          ])],
        });
  }

  resetNewPassword(passwordResetVerifyForm: FormGroup){
      this.resetNewPasswordForm.get('password')?.markAsTouched();
      if(this.resetNewPasswordForm.valid){
        const token = this.resetNewPasswordForm.get('token')?.value;
        const password = this.resetNewPasswordForm.get('password')?.value;
        this.authService.newPasswordToResetPassword(this.userEmail, password).subscribe(res => {
          if(res.message === "Success"){
            this.passwordResetSuccessful = true;
          }
        }, error => {
          if(error && error.message === "Password Not Saved"){
            this.passwordResetFailed = true;
          }
        });
      }
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  navigateToLogin(){
    this.router.navigate(['../../sign-in', {data: {passwordResetSuccessful: "password-reset-success"}}], { relativeTo: this.activiatedRoute });
  }

  private resetButtons(){
    this.passwordResetFailed = false;
    this.passwordResetSuccessful = false;
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

}
