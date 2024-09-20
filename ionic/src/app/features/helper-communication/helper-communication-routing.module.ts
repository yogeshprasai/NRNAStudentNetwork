import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HelperCommunicationPage } from './helper-communication.page';
import { SearchComponent } from './search/search.component';
import { HelperComponent } from './helper/helper.component';
import { CommunicationComponent } from './communication/communication.component';
import { HelperResolverService } from 'src/app/shared/resolvers/helper-resolver.service';

const routes: Routes = [
  {
    path: '',
    component: HelperCommunicationPage,
    children: [
      {
        path: '',
        redirectTo: 'helper',
        pathMatch: 'full'
      },
      {
        path: 'helper',
        component: HelperComponent,
        resolve: {
          helper: HelperResolverService
        }
      },
      {
        path: 'communication',
        component: CommunicationComponent
      },
      {
        path: 'search',
        component: SearchComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HelperCommunicationRoutingModule {}
