import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NrnaErrorComponent } from './components/nrna-error/nrna-error.component';
import { IonicModule } from '@ionic/angular';
import { Validation_ProfilePage } from './validation';
import { SelectPopoverComponent } from './components/select-popover/select-popover.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CustomDatePipe} from "./pipes/custom-date.pipe";



@NgModule({
  declarations: [
    NrnaErrorComponent,
    SelectPopoverComponent,
    CustomDatePipe
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
    CustomDatePipe
  ]
})
export class SharedModule { }
