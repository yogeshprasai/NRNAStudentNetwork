import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from "../../../../../shared/service/auth.service";
import {Validation_Password_Reset_Send} from "../../../../../shared/validation";
import {NrnaRoutes} from "../../../../../shared/service/constant";

@Component({
  selector: 'nrna-send-token',
  templateUrl: './send-token-reset-password.component.html',
  styleUrls: ['./send-token-reset-password.component.scss'],
})
export class SendTokenResetPasswordComponent implements OnInit {

  public noEmailExist: boolean = false;
  public passwordResetForm: FormGroup = new FormGroup({});
  public Validation_Password_Reset_Send = Validation_Password_Reset_Send;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.passwordResetForm = this.formBuilder.group({
      email: ['', Validators.compose([
        Validators.required,
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"),
      ])],
    });
  }

  ionViewWillEnter() {
    this.passwordResetForm.reset();
  }

  async sendEmailAndToken(passwordResetForm: FormGroup) {
    this.passwordResetForm.get('email')?.setErrors(null);
      const credentials = {
        email: passwordResetForm.value.email,
      };
      this.authService.sendTokenEmailToResetPassword(credentials.email).subscribe({
        next: data => {
          if(data.message === "Email Exist") {
              this.router.navigate([NrnaRoutes.VerifyToken, {email: credentials.email}], {relativeTo: this.route});
            }  else if(data.message && data.message === "No Email Exist") {
              this.passwordResetForm.get('email')?.setErrors({'noEmailExist': true});
            }
          },
        error: error => {
          if(error.message && error.message.includes("User not found")) {
            this.passwordResetForm.get('email')?.setErrors({'noUserExist': true});
          }else if(error.message && error.message.includes("Email address is not verified")) {
            this.passwordResetForm.get('email')?.setErrors({'emailNotVerified': true});
          }else {
            this.passwordResetForm.get('email')?.setErrors({'emailSendingFailed': true});
          }
      }});
  }

}
