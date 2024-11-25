import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { SearchComponent } from './search/search.component';
import { StudentComponent } from './student/student.component';
import { SharedModule } from '../../shared/shared.module';
import { StudentCommunicationRoutingModule } from './student-communication-routing.module';
import { StudentCommunicationPage } from './student-communication.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    StudentCommunicationRoutingModule,
    SharedModule
  ],
  declarations: [StudentCommunicationPage, StudentComponent, SearchComponent]
})
export class StudentCommunicationPageModule {}
