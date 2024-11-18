import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HelperCommunicationPage } from './helper-communication.page';
import { SearchComponent } from './search/search.component';
import { HelperComponent } from './helper/helper.component';
import { HelperResolverService } from 'src/app/shared/resolvers/helper-resolver.service';

const routes: Routes = [
  {
    path: '',
    component: HelperCommunicationPage,
    resolve: {
      helper: HelperResolverService
    },
    children: [
      {
        path: '',
        redirectTo: 'helper',
        pathMatch: 'full'
      },
      {
        path: 'helper',
        component: HelperComponent,
        pathMatch: 'full'
      },
      {
        path: 'search',
        component: SearchComponent,
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HelperCommunicationRoutingModule {}
