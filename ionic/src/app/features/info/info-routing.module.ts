import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutUsComponent } from './about-us/about-us.component';
import { InfoComponent } from './info.component';
import { CollegeSearchComponent } from './college-search/college-search.component';
import { JobsComponent } from './jobs/jobs.component';


const routes: Routes = [
  {
    path: '',
    component: InfoComponent,
    children: [
      {
        path: '',
        component: AboutUsComponent,
        pathMatch: 'full'
      },
      {
        path: 'about-us',
        component: AboutUsComponent
      },
      {
        path: 'college-search',
        component: CollegeSearchComponent
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
