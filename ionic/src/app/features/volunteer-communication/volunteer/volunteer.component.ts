import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {UsersService} from "../../../shared/service/users.service";
import {ViewDidEnter, ViewWillEnter} from "@ionic/angular";

@Component({
  selector: 'nrna-volunteer',
  templateUrl: './volunteer.component.html',
  styleUrls: ['./volunteer.component.scss']
})
export class VolunteerComponent implements OnInit, ViewWillEnter, ViewDidEnter {

  public volunteers: any = [];

  constructor(private route: ActivatedRoute, private usersService: UsersService) {

  }

  ngOnInit() {

  }

  ionViewWillEnter() {
    this.updateVolunteerInfo();
  }

  ionViewDidEnter(){
    this.updateVolunteerInfo();
  }

  updateVolunteerInfo(){
    this.route?.data.subscribe((response: any) => {
      if(response.allVolunteers){
        this.volunteers = response.allVolunteers;
      }
    });
  }

  getProfilePic(base64String: string): string{
    return "data:image/jpeg;base64," + base64String;
  }

}
