import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NrnaErrorComponent } from './components/nrna-error/nrna-error.component';
import { IonicModule } from '@ionic/angular';
import { Validation_ProfilePage } from './validation';



@NgModule({
  declarations: [
    NrnaErrorComponent
  ],
  imports: [
    CommonModule,
    IonicModule
  ],
  exports: [
    NrnaErrorComponent
  ]
})
export class SharedModule { }
