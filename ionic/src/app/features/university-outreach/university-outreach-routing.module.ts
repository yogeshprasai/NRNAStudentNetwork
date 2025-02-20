import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";
import {UniversityOutreachComponent} from "./university-outreach.component";

const routes: Routes = [
    {
        path: '',
        component: UniversityOutreachComponent,
        children: [
            {
                path: '',
                component: UniversityOutreachComponent
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class UniversityOutreachRoutingModule {}