import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VolunteerCommunicationPage } from './volunteer-communication.page';
import { SearchComponent } from './search/search.component';
import { VolunteerComponent } from './volunteer/volunteer.component';
import {VolunteerResolverService} from "../../shared/resolvers/volunteer-resolver.service";

const routes: Routes = [
  {
    path: '',
    component: VolunteerCommunicationPage,
    children: [
      {
        path: '',
        redirectTo: 'volunteer',
        pathMatch: 'full'
      },
      {
        path: 'volunteer',
        component: VolunteerComponent,
        pathMatch: 'full',
        resolve: {
          allVolunteers: VolunteerResolverService
        },
      },
      {
        path: 'search',
        component: SearchComponent,
        pathMatch: 'full',
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class VolunteerCommunicationRoutingModule {}
