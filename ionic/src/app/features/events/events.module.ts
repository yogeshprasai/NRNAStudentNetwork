import { CommonModule } from "@angular/common";
import { EventsComponent } from "./events.component";
import { IonicModule } from "@ionic/angular";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SharedModule } from "src/app/shared/shared.module";
import { EventsRoutingModule } from "./events-routing.module";
import { NgModule } from "@angular/core";
import { UpcomingEventsComponent } from "./upcoming-events/upcoming-events.component";
import { PastEventsComponent } from "./past-events/past-events.component";

@NgModule({
    declarations: [
        EventsComponent,
        UpcomingEventsComponent,
        PastEventsComponent
    ],
    imports: [
        CommonModule,
        IonicModule,
        FormsModule,
        ReactiveFormsModule,
        EventsRoutingModule,
        SharedModule,
    ],
    providers: []
})

export class EventsModule {}