import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { AuthRouterModule } from './auth-routing.module';
import { SignInComponent } from './sign-in/sign-in.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SignUpComponent } from './sign-up/sign-up.component';
import { SharedModule } from 'src/app/shared/shared.module';
import {PasswordResetSendComponent} from "./reset/password-reset-token/password-reset-send.component";
import {PasswordResetVerifyComponent} from "./reset/password-reset-verify/password-reset-verify.component";

@NgModule({
  declarations: [
    SignInComponent,
    SignUpComponent,
    PasswordResetSendComponent,
    PasswordResetVerifyComponent
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
