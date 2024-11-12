import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';
import { InfoRoutingModule } from './info-routing.module';
import { AboutUsComponent } from './about-us/about-us.component';
import { InfoComponent } from './info.component';
import { CollegeSearchComponent } from './college-search/college-search.component';
import { JobsComponent } from './jobs/jobs.component';



@NgModule({
  declarations: [
    InfoComponent,
    AboutUsComponent,
    CollegeSearchComponent,
    JobsComponent
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
