import { CommonModule } from "@angular/common";
import { IonicModule } from "@ionic/angular";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SharedModule } from "src/app/shared/shared.module";
import { UniversityOutreachRoutingModule } from "./university-outreach-routing.module";
import { NgModule } from "@angular/core";
import {UniversityOutreachComponent} from "./university-outreach.component";


@NgModule({
    declarations: [
        UniversityOutreachComponent
    ],
    imports: [
        CommonModule,
        IonicModule,
        FormsModule,
        ReactiveFormsModule,
        UniversityOutreachRoutingModule,
        SharedModule,
    ],
    providers: []
})

export class UniversityOutreachModule {}