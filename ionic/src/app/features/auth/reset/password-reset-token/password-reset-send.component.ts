import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertController, LoadingController, ToastController} from '@ionic/angular';
import {AuthService} from "../../../../shared/service/auth.service";
import {Validation_Password_Reset_Send} from "../../../../shared/validation";

@Component({
  selector: 'nrna-password-reset-token',
  templateUrl: './password-reset-send.component.html',
  styleUrls: ['./password-reset-send.component.scss'],
})
export class PasswordResetSendComponent implements OnInit {

  public noEmailExist: boolean = false;
  public passwordResetForm: FormGroup = new FormGroup({});
  public Validation_Password_Reset_Send = Validation_Password_Reset_Send;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private loadingCtrl: LoadingController,
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
    const loading = await this.loadingCtrl.create({
      message: 'Sending Request...',
    });
    this.passwordResetForm.get('email')?.setErrors(null);
      const credentials = {
        email: passwordResetForm.value.email,
      };
      loading.present();
      this.authService.sendEmailAndToken(credentials.email).subscribe(data => {
        loading.dismiss();
        if(data.message === "Email Exist") {
          this.router.navigate(['./password-reset-verify', {email: credentials.email}], {relativeTo: this.route});
        }  else if(data.message && data.message === "No Email Exist") {
          this.passwordResetForm.get('email')?.setErrors({'noEmailExist': true});
        }
      }, error => {
        console.log(error);
        loading.dismiss();
        if(error.message && error.message.includes("Email address is not verified")) {
          this.passwordResetForm.get('email')?.setErrors({'emailNotVerified': true});
        }else {
          this.passwordResetForm.get('email')?.setErrors({'emailSendingFailed': true});
        }
      })
  }

}
