import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { AuthGuard } from 'src/app/shared/guards/auth.guard';

import {ProfileResolverService} from "../../shared/resolvers/profile-resolver.service";

const routes: Routes = [
  {
    path: '',
    component: ProfileComponent,
    resolve : {
      profile: ProfileResolverService,
    },
    canActivate: [AuthGuard],
    children : [
      {
        path: '',
        component: ProfileComponent,
        pathMatch: 'full',
        resolve: {
          profile: ProfileResolverService
        }
      },
      {
        path: 'profile',
        component: ProfileComponent,
        canActivate: [AuthGuard],
        resolve: {
          profile: ProfileResolverService
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserDashboardRoutingModule {}
