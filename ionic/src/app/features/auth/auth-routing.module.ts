import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { SignInComponent } from "./sign-in/sign-in.component";
import { SignUpComponent } from "./sign-up/sign-up.component";
import { LoggedInUserGuard } from "src/app/shared/guards/logged-in-user.guard";
import {SendTokenResetPasswordComponent} from "./reset/reset-password/send-token/send-token-reset-password.component";
import {VerifyTokenResetPasswordComponent} from "./reset/reset-password/verify-token/verify-token-reset-password.component";
import {
    NewPasswordResetPasswordComponent
} from "./reset/reset-password/create-new-password/new-password-reset-password.component";


const routes: Routes = [
    {
        path: '',
        redirectTo: 'sign-in',
        pathMatch: 'full'
    },
    {
        path: 'sign-in',
        component: SignInComponent,
        canActivate: [LoggedInUserGuard]
    },
    {
        path: 'sign-up',
        component: SignUpComponent
    },
    {
        path: 'reset-password',
        children : [
            {
                path: 'send-token',
                component: SendTokenResetPasswordComponent,
                pathMatch: "full"
            },
            {
                path: 'verify-token',
                component: VerifyTokenResetPasswordComponent,
            },
            {
                path: 'create-new-password',
                component: NewPasswordResetPasswordComponent
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class AuthRouterModule {}