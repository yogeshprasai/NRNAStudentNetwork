import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {AlertController} from "@ionic/angular";
import {AuthService} from "../../shared/service/auth.service";

@Component({
  selector: 'nrna-student',
  templateUrl: './student-communication.page.html',
  styleUrls: ['./student-communication.page.scss'],
})
export class StudentCommunicationPage implements OnInit {

  constructor(private authService: AuthService, private alertController: AlertController) {}

  ngOnInit() {
  }

  async ionViewWillEnter(){
    const alert = await this.alertController.create({
      header: 'Requires Login',
      subHeader: '',
      message: 'Please Login to see students list',
      buttons: ['OK']
    });
    if(!this.authService.isLoggedIn){
      await alert.present();
    }

  }
  
}
