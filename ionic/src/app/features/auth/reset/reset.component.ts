import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertController, LoadingController } from '@ionic/angular';
import { Validation_ResetPassword } from 'src/app/shared/validation';

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
    private alertController: AlertController
  ) { }

  ngOnInit() {
    this.passwordResetForm = this.formBuilder.group({
      email: ['', Validators.compose([
        Validators.required,
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"),
      ])],
    });
  }

  submitCredentials(passwordResetForm: FormGroup): void {
    if (!passwordResetForm.valid) {
      console.log('Form is not valid yet, current value:', passwordResetForm.value);
    } else {
      const credentials = {
        email: passwordResetForm.value.email,
      };
      this.resetPassword(credentials);
    }
  }

  private resetPassword(credentials: any){

  }

}
