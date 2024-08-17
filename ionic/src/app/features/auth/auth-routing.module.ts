import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { SignInComponent } from "./sign-in/sign-in.component";
import { SignUpComponent } from "./sign-up/sign-up.component";
import { ResetComponent } from "./reset/reset.component";
import { LoggedInUserGuard } from "src/app/shared/guards/logged-in-user.guard";


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
        component: ResetComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class AuthRouterModule {}