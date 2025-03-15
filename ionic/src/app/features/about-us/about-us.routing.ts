import { Routes, RouterModule } from '@angular/router';
import { AboutUsComponent } from './about-us.component';
import { NgModule } from '@angular/core';
import { OurMissionComponent } from './our-mission/our-mission.component';
import { BoardComponent } from './board/board.component';

const routes: Routes = [
  {
    path: '',
    component: AboutUsComponent,
    children: [
      {
        path: '',
        redirectTo: 'mission',
        pathMatch: 'full'
      },
      {  path: 'mission',
         component: OurMissionComponent
      },
      {
        path: 'board',
        component: BoardComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AboutUsRoutingModule {}
