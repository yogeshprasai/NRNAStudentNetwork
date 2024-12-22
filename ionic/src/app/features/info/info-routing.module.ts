import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsefulInfoComponent } from './useful-info/useful-info.component';
import { InfoComponent } from './info.component';
import { CollegeSearchComponent } from './college-search/college-search.component';
import { JobsComponent } from './jobs/jobs.component';
import { UscisComponent } from './uscis/uscis.component';
import {TopUniversitiesResolverService} from "../../shared/resolvers/top-universities-resolver.service";


const routes: Routes = [
  {
    path: '',
    component: InfoComponent,
    children: [
      {
        path: '',
        component: UsefulInfoComponent,
        pathMatch: 'full'
      },
      {
        path: 'embassy',
        component: UsefulInfoComponent
      },
      {
        path: 'uscis',
        component: UscisComponent
      },
      {
        path: 'college-search',
        component: CollegeSearchComponent,
        resolve: {
          topUniversities: TopUniversitiesResolverService
        }
      },
      {
        path: 'jobs',
        component: JobsComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InfoRoutingModule {}
