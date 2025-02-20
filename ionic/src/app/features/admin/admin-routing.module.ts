import {RouterModule, Routes} from "@angular/router";
import { AdminComponent } from "./admin.component";
import {NgModule} from "@angular/core";
import {VolunteerResolverService} from "../../shared/resolvers/volunteer-resolver.service";

const routes: Routes = [
    {
        path: '',
        redirectTo: '',
        pathMatch: 'full'
    },
    {
        path: '',
        component: AdminComponent,
        resolve: {
            appliedForVolunteers: VolunteerResolverService
        },
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule {}

