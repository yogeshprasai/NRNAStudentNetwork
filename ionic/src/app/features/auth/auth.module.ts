import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { AuthRouterModule } from './auth-routing.module';
import { SignInComponent } from './sign-in/sign-in.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SignUpComponent } from './sign-up/sign-up.component';
import { SharedModule } from 'src/app/shared/shared.module';
import {SendTokenResetPasswordComponent} from "./reset/reset-password/send-token/send-token-reset-password.component";
import {VerifyTokenResetPasswordComponent} from "./reset/reset-password/verify-token/verify-token-reset-password.component";
import { NewPasswordResetPasswordComponent } from './reset/reset-password/create-new-password/new-password-reset-password.component';

@NgModule({
  declarations: [
    SignInComponent,
    SignUpComponent,
    SendTokenResetPasswordComponent,
    VerifyTokenResetPasswordComponent,
    NewPasswordResetPasswordComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
    ReactiveFormsModule,
    AuthRouterModule,
    SharedModule
  ]
})
export class AuthModule { }
