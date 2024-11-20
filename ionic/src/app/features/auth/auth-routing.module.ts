import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { SignInComponent } from "./sign-in/sign-in.component";
import { SignUpComponent } from "./sign-up/sign-up.component";
import { LoggedInUserGuard } from "src/app/shared/guards/logged-in-user.guard";
import {PasswordResetSendComponent} from "./reset/password-reset-token/password-reset-send.component";
import {PasswordResetVerifyComponent} from "./reset/password-reset-verify/password-reset-verify.component";


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
        path: 'password-reset',
        children : [
            {
                path: '',
                component: PasswordResetSendComponent,
                pathMatch: "full"
            },
            {
                path: 'password-reset-verify',
                component: PasswordResetVerifyComponent,
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class AuthRouterModule {}