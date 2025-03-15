import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {NrnaRoutes} from "../../shared/service/constant";
import {AlertController, ViewWillEnter} from "@ionic/angular";
import {AuthService} from "../../shared/service/auth.service";

@Component({
  selector: 'volunteer-communication',
  templateUrl: './volunteer-communication.page.html',
  styleUrls: ['./volunteer-communication.page.scss'],
})
export class VolunteerCommunicationPage implements OnInit, ViewWillEnter {

  constructor(private authService: AuthService, private alertController: AlertController,
              private router: Router) {}

  ngOnInit() {
    
  }

  async ionViewWillEnter(){
    const alert = await this.alertController.create({
      header: 'Login Required',
      subHeader: '',
      message: 'Please Login to see volunteer list',
      buttons: this.loginRequiredButtons,
      backdropDismiss: false
    });
    if(!this.authService.isLoggedIn){
      await alert.present();
    }

  }

  public loginRequiredButtons = [
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.router.navigate([NrnaRoutes.Login]);
      },
    },
  ];
  
}
