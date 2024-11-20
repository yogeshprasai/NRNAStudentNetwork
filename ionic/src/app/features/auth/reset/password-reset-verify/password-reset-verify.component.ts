import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Validation_Password_Reset_Verify} from "../../../../shared/validation";
import {AuthService} from "../../../../shared/service/auth.service";
import {ToastController} from "@ionic/angular";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-password-reset-verify',
  templateUrl: './password-reset-verify.component.html',
  styleUrls: ['./password-reset-verify.component.css']
})
export class PasswordResetVerifyComponent implements OnInit {

  public userEmail: string = "";
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
        this.presentToast("Token Sent in your email. Check your spam too");
      }else {
        this.presentToast("Something went wrong. Please try again");
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
          this.router.navigate(['../../sign-in', {data: {passwordResetSuccessful: "password-reset-success"}}], { relativeTo: this.activiatedRoute });
        }
      });
    }
  }

  async presentToast(text: any) {
    const toast = await this.toastCtrl.create({
      message: text,
      duration: 3000
    });
    toast.present();
  }

}
