import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NrnaErrorComponent } from './components/nrna-error/nrna-error.component';
import { IonicModule } from '@ionic/angular';
import { Validation_ProfilePage } from './validation';
import { SelectPopoverComponent } from './components/select-popover/select-popover.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [
    NrnaErrorComponent,
    SelectPopoverComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    NrnaErrorComponent
  ]
})
export class SharedModule { }
