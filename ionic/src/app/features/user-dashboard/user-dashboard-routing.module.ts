import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { AuthGuard } from 'src/app/shared/guards/auth.guard';
import { AddressComponent } from './address/address.component';
import { UserDashboardComponent } from './user-dashboard.component';
import {ProfileResolverService} from "../../shared/resolvers/profile-resolver.service";
import {AddressResolverService} from "../../shared/resolvers/address-resolver.service";

const routes: Routes = [
  {
    path: '',
    component: UserDashboardComponent,
    children : [
      {
        path: '',
        component: ProfileComponent,
        pathMatch: 'full'
      },
      {
        path: 'profile',
        component: ProfileComponent,
        canActivate: [AuthGuard],
        resolve: {
          profile: ProfileResolverService
        }
      },
      {
        path: 'address',
        component: AddressComponent,
        canActivate: [AuthGuard],
        resolve: {
          address: AddressResolverService
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
