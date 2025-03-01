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
  ) { }

  ngOnInit() {
    this.passwordResetForm = this.formBuilder.group({
      email: ['', Validators.compose([
        Validators.required,
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"),
      ])],
    });
  }

  sendEmailAndToken(passwordResetForm: FormGroup): void {
      const credentials = {
        email: passwordResetForm.value.email,
      };
      this.authService.sendEmailAndToken(credentials.email).subscribe(res => {
        console.log(res);
        if(res.message === "Email Exist") {
          this.router.navigate(['./password-reset-verify', {email: credentials.email}], {relativeTo: this.route});
        }else if(res.message === "Error Sending Email"){
          this.passwordResetForm.get('email')?.setErrors({'emailSendingFailed': true});
        }else if(!res.message){
          this.passwordResetForm.get('email')?.setErrors({'noMessageReceived': true});
        }
        else {
          this.passwordResetForm.get('email')?.setErrors({'noEmailExist': true});
        }
      })
  }

}
