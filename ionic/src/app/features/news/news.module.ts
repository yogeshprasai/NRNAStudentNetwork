import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewsComponent } from './news.component';
import {NewsRoutingModule} from "./news-routing.module";
import {IonicModule} from "@ionic/angular";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    NewsRoutingModule,
    IonicModule,
    ReactiveFormsModule
  ],
  declarations: [NewsComponent]
})
export class NewsModule { }
