import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';
import { InfoRoutingModule } from './info-routing.module';
import { UsefulInfoComponent } from './useful-info/useful-info.component';
import { InfoComponent } from './info.component';
import { CollegeSearchComponent } from './college-search/college-search.component';
import { JobsComponent } from './jobs/jobs.component';
import { UscisComponent } from './uscis/uscis.component';



@NgModule({
  declarations: [
    InfoComponent,
    UsefulInfoComponent,
    CollegeSearchComponent,
    JobsComponent,
    UscisComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    InfoRoutingModule
  ]
})
export class InfoModule { }
