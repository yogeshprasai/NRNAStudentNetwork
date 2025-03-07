import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Validation_Password_Reset_Verify} from "../../../../../shared/validation";
import {AuthService} from "../../../../../shared/service/auth.service";
import {AlertController, ToastController} from "@ionic/angular";
import {ActivatedRoute, Router} from "@angular/router";
import {NrnaRoutes} from "../../../../../shared/service/constant";
import {interval, Subscription} from "rxjs";

@Component({
  selector: 'nrna-token-verify',
  templateUrl: './verify-token-reset-password.component.html',
  styleUrls: ['./verify-token-reset-password.component.css']
})
export class VerifyTokenResetPasswordComponent implements OnInit, OnDestroy {

  readonly COUNTDOWN_IN_SECONDS = 30;
  public userEmail: string = "";
  public invalidToken: boolean = false;
  public errorSendingNewToken: boolean = false;
  public successSendingNewToken: boolean = false;
  buttonText = 'Start';
  isTimerRunning = false;
  isButtonDisabled = false;
  private timerSubscription?: Subscription;
  private countdownSeconds = this.COUNTDOWN_IN_SECONDS;
  
  public Validation_Password_Reset_Verify = Validation_Password_Reset_Verify;
  public passwordResetVerifyForm: FormGroup = new FormGroup({});

  constructor(private activiatedRoute: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private authService: AuthService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.userEmail = this.activiatedRoute.snapshot.params['email'];
    this.passwordResetVerifyForm = this.formBuilder.group({
      token: ['', Validators.compose([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(6)
      ])]
    });
  }

  async resendToken(){
    this.authService.sendTokenEmailToResetPassword(this.userEmail).subscribe(res => {
      this.startTimer();
      if(res.message === "Email Exist") {
        this.successSendingNewToken = true;
      }else {
        this.errorSendingNewToken = true;
      }
    })
  }

  startTimer() {
    this.isTimerRunning = true;
    this.isButtonDisabled = true;
    this.buttonText = this.countdownSeconds.toString();

    this.timerSubscription = interval(1000).subscribe(() => {
      this.countdownSeconds--;
      this.buttonText = this.countdownSeconds.toString();
      if (this.countdownSeconds <= 0) {
        this.isButtonDisabled = false;
        this.isTimerRunning = false;
        if (this.timerSubscription) {
          this.countdownSeconds = 60;
          this.timerSubscription.unsubscribe();
        }
      }
    });
  }

  tokenVerifyToResetPassword(){
    this.passwordResetVerifyForm.get('token')?.markAsTouched();
    if(this.passwordResetVerifyForm.valid){
      const token = this.passwordResetVerifyForm.get('token')?.value;
      const password = this.passwordResetVerifyForm.get('password')?.value;
      this.authService.verifyTokenToResetPassword(this.userEmail, token).subscribe({
        next: (res: any) => {
          if(res.message === "Success"){
            this.router.navigate([NrnaRoutes.CreateNewPassword, {email: this.userEmail}], {relativeTo: this.route});
          }
        },
        error: error => {
          if(error && error.message === "Invalid Token"){
            this.invalidToken = true;
          }
        }});
    }
  }

  public resetButtons(){
    this.invalidToken = false
    this.errorSendingNewToken = false;
    this.successSendingNewToken = false;
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

  ngOnDestroy() {
    if (this.timerSubscription) {
      this.timerSubscription.unsubscribe();
    }
  }

}
