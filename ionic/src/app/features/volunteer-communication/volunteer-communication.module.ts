import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { SearchComponent } from './search/search.component';
import { VolunteerComponent } from './volunteer/volunteer.component';
import { SharedModule } from '../../shared/shared.module';
import { VolunteerCommunicationRoutingModule } from './volunteer-communication-routing.module';
import { VolunteerCommunicationPage } from './volunteer-communication.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    VolunteerCommunicationRoutingModule,
    SharedModule
  ],
  declarations: [VolunteerCommunicationPage, VolunteerComponent, SearchComponent]
})
export class VolunteerCommunicationModule {}
