import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/shared/guards/auth.guard';
import { AboutUsComponent } from './about-us/about-us.component';


const routes: Routes = [
  {
    path: '',
    component: AboutUsComponent,
    pathMatch: 'full'
  },
  {
    path: '',
    component: AboutUsComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InfoRoutingModule {}
