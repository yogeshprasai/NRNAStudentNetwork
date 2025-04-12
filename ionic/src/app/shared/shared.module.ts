import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NrnaErrorComponent } from './components/nrna-error/nrna-error.component';
import { IonicModule } from '@ionic/angular';
import { Validation_ProfilePage } from './validation';
import { SelectPopoverComponent } from './components/select-popover/select-popover.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CustomDatePipe} from "./pipes/custom-date.pipe";
import { PhoneFormatPipe } from './pipes/phone-format.pipe';



@NgModule({
  declarations: [
    NrnaErrorComponent,
    SelectPopoverComponent,
    CustomDatePipe,
    PhoneFormatPipe
  ],
  imports: [
    CommonModule,
    IonicModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    NrnaErrorComponent,
    SelectPopoverComponent,
    CustomDatePipe,
    PhoneFormatPipe
  ]
})
export class SharedModule { }
