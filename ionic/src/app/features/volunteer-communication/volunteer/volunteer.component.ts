import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {UsersService} from "../../../shared/service/users.service";
import {ViewDidEnter, ViewWillEnter} from "@ionic/angular";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'nrna-volunteer',
  templateUrl: './volunteer.component.html',
  styleUrls: ['./volunteer.component.scss']
})
export class VolunteerComponent implements OnInit, ViewWillEnter, ViewDidEnter {


  public volunteers: any = [];
  showCheckbox: boolean = false;
  showSendEmailButton: boolean = false;
  allSelectedVolunteersEmails: any = [];
  formGroup: FormGroup = new FormGroup({});


  constructor(private route: ActivatedRoute, private fb: FormBuilder) {

  }

  ngOnInit() {

    this.formGroup = this.fb.group({
      checkbox: [],
    })
  }

  ionViewWillEnter() {
    this.updateVolunteerInfo();
    this.resetEmailStatus();
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

  selectVolunteersToEmail(){
    this.showCheckbox = true;
  }

  selectedCheckbox(volunteerEmail: string){
    if(this.allSelectedVolunteersEmails.includes(volunteerEmail)){
      this.allSelectedVolunteersEmails = this.allSelectedVolunteersEmails.filter((email: any) => email !== volunteerEmail);
    }else{
      this.allSelectedVolunteersEmails.push(volunteerEmail);
    }
    console.log(this.allSelectedVolunteersEmails);
    if(this.allSelectedVolunteersEmails.length){
      this.showSendEmailButton = true;
    }
  }

  sendEmailToVoluntters(){
    window.location.href = 'mailto:' + this.allSelectedVolunteersEmails.toString();
    this.resetEmailStatus();
  }

  cancelEmailToVolunteers(){
    this.resetEmailStatus();
  }

  resetEmailStatus(){
    this.allSelectedVolunteersEmails = [];
    this.showCheckbox = false;
    this.showSendEmailButton = false;
  }

}
