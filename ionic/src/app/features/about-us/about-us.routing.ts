import { Routes, RouterModule } from '@angular/router';
import { AboutUsComponent } from './about-us.component';
import { NgModule } from '@angular/core';

const routes: Routes = [
  {  path: '',
     component: AboutUsComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AboutUsRoutingModule {}
