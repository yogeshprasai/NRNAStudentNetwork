import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";
import {UniversityOutreachComponent} from "./university-outreach.component";
import {VolunteerResolverService} from "../../shared/resolvers/volunteer-resolver.service";

const routes: Routes = [
    {
        path: '',
        component: UniversityOutreachComponent,
        resolve: {
            allVolunteers: VolunteerResolverService
        },
        children: [
            {
                path: '',
                component: UniversityOutreachComponent,
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class UniversityOutreachRoutingModule {}