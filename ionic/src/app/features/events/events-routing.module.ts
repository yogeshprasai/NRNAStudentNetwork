import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { EventsComponent } from "./events.component";
import { PastEventsComponent } from "./past-events/past-events.component";
import { UpcomingEventsComponent } from "./upcoming-events/upcoming-events.component";

const routes: Routes = [
    {
        path: '',
        component: EventsComponent,
        children: [
            {
            path: '',
            redirectTo: 'upcoming-events',
            pathMatch: 'full'
            },
            {
            path: 'upcoming-events',
            component: UpcomingEventsComponent,
            pathMatch: 'full',
            },
            {
            path: 'past-events',
            component: PastEventsComponent,
            pathMatch: 'full',
            }
        ]
    }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EventsRoutingModule {}