import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {UsersService} from "../../../shared/service/users.service";

@Component({
  selector: 'nrna-volunteer',
  templateUrl: './volunteer.component.html',
  styleUrls: ['./volunteer.component.scss'],
})
export class VolunteerComponent implements OnInit {

  public folder!: string;
  private activatedRoute = inject(ActivatedRoute);
  public volunteers: any = [];

  constructor(private route: ActivatedRoute, private usersService: UsersService) {

  }

  ngOnInit() {

  }

  updateVolunteerInfo(){
    //console.log(this.route);
    this.route?.data.subscribe((response: any) => {
      if(response.allVolunteers){
        this.volunteers = response.allVolunteers.filter((volunteer: any) => volunteer.isVolunteer);
      }
    });
  }

  ionViewWillEnter() {
    this.updateVolunteerInfo();
  }

  ionViewDidEnter(){
    this.updateVolunteerInfo();
  }

  getProfilePic(base64String: string): string{
    return "data:image/jpeg;base64," + base64String;
  }

}
