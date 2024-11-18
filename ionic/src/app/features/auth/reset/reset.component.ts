import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {AlertController, LoadingController, ToastController} from '@ionic/angular';
import { Validation_ResetPassword } from 'src/app/shared/validation';
import {AuthService} from "../../../shared/service/auth.service";

@Component({
  selector: 'nrna-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.scss'],
})
export class ResetComponent  implements OnInit {

  public passwordResetForm: FormGroup = new FormGroup({});
  public validationResetPass = Validation_ResetPassword;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private loadingCtrl: LoadingController,
    private alertController: AlertController,
    private authService: AuthService,
    private toastCtrl: ToastController
  ) { }

  ngOnInit() {
    this.passwordResetForm = this.formBuilder.group({
      email: ['', Validators.compose([
        Validators.required,
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"),
      ])],
    });
  }

  checkIfEmailExist(passwordResetForm: FormGroup): void {
    if (!passwordResetForm.valid) {
      console.log('Form is not valid yet, current value:', passwordResetForm.value);
    } else {
      const credentials = {
        email: passwordResetForm.value.email,
      };
      this.authService.isEmailExist(credentials.email).subscribe(res => {
        if(res.message === "Email Exist") {
          this.presentToast("Reset Password Email Sent. Check your spam too");
        }else {
          this.presentToast("No Email Registered with above id");
        }
      })
    }
  }

  private resetPassword(credentials: any){

  }

  async presentToast(text: any) {
    const toast = await this.toastCtrl.create({
      message: text,
      duration: 3000
    });
    toast.present();
  }

}
