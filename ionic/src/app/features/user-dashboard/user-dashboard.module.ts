import { NgModule } from "@angular/core";
import {CommonModule, Location} from "@angular/common";
import { IonicModule } from "@ionic/angular";
import { UserDashboardRoutingModule } from "./user-dashboard-routing.module";
import { ProfileComponent } from "./profile/profile.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SharedModule } from "src/app/shared/shared.module";
import { UserDashboardComponent } from "./user-dashboard.component";


@NgModule({
    declarations: [
        UserDashboardComponent,
        ProfileComponent
    ],
    imports: [
        CommonModule,
        IonicModule,
        FormsModule,
        ReactiveFormsModule,
        UserDashboardRoutingModule,
        SharedModule,
    ],
    providers: []
})

export class UserDashboardModule {}