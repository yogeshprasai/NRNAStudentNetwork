import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SearchComponent } from './search/search.component';
import {StudentCommunicationPage} from "./student-communication.page";
import {StudentComponent} from "./student/student.component";
import {StudentResolverService} from "../../shared/resolvers/student-resolver.service";

const routes: Routes = [
  {
    path: '',
    component: StudentCommunicationPage,
    children: [
      {
        path: '',
        redirectTo: 'student',
        pathMatch: 'full'
      },
      {
        path: 'student',
        component: StudentComponent,
        pathMatch: 'full',
        resolve: {
          allStudents: StudentResolverService
        },
      },
      {
        path: 'search',
        component: SearchComponent,
        pathMatch: 'full',
        resolve: {
          allStudents: StudentResolverService
        },
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentCommunicationRoutingModule {}
