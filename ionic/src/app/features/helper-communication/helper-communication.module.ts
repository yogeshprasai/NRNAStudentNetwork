import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { SearchComponent } from './search/search.component';
import { HelperComponent } from './helper/helper.component';
import { CommunicationComponent } from './communication/communication.component';
import { SharedModule } from '../../shared/shared.module';
import { HelperCommunicationRoutingModule } from './helper-communication-routing.module';
import { HelperCommunicationPage } from './helper-communication.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    HelperCommunicationRoutingModule,
    SharedModule
  ],
  declarations: [HelperCommunicationPage, HelperComponent, CommunicationComponent, SearchComponent]
})
export class HelperCommunicationPageModule {}
