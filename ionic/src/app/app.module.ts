import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AuthService } from './shared/service/auth.service';
import { Storage } from '@ionic/storage';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { LoadingService } from './shared/service/loading.service';
import { ProfileAddressService } from './shared/service/profile-address.service';
import {AuthInterceptor} from "./shared/service/interceptors/auth-interceptors";

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule, 
    FormsModule,
    ReactiveFormsModule, 
    IonicModule.forRoot(), 
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [{ provide: RouteReuseStrategy, useClass: IonicRouteStrategy }, AuthService, Storage, 
                LoadingService, ProfileAddressService,
                { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    ],
  bootstrap: [AppComponent],
})
export class AppModule {}
