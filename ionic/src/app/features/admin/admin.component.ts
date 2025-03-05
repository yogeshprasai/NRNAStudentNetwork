import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProfileAddressService} from "../../shared/service/profile-address.service";
import {concatMap} from "rxjs/operators";
import {UsersService} from "../../shared/service/users.service";
import {catchError, of} from "rxjs";
import {UserProfile} from "../../shared/model/user-profile";
import {AlertController} from "@ionic/angular";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  appliedForVolunteers: any;
  volunteerUpdateSuccess: boolean = false;

  constructor(private route: ActivatedRoute, private usersService: UsersService,
              private profileAddressService: ProfileAddressService, private alertController: AlertController) { }

  ngOnInit() {
    this.route.data.subscribe((resolveResponse: any) => {
      this.appliedForVolunteers  = resolveResponse.appliedForVolunteers;
    })
  }

  profileApprovalByAdmin(volunteer: any): void{
    this.profileAddressService.profileApprovalByAdmin(volunteer).pipe(
        concatMap((res: any) => {
            if(res.message === "Success"){
              volunteer.isApplyForVolunteer = true;
              this.volunteerUpdateSuccess = true;
              return this.usersService.getAllVolunteers();
            }else{
              this.showErrorAlert("Failed to approve volunteer. Please try again.")
              return of(null);
            }
          }),
        catchError(err => of('Error Handled ' + err.message))
    ).subscribe((userProfiles: UserProfile[]) => {
      if(typeof(userProfiles) == "object"){
        this.appliedForVolunteers = userProfiles
            .filter((user: UserProfile) => user.isApplyForVolunteer);
      }
    }, error => {
      alert(error.message);
    });
  }

  private resetButtons(){
    this.volunteerUpdateSuccess = false;
  }

  public successButtons = [
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.resetButtons();
      },
    },
  ];

  async showErrorAlert(message: string){
    const showErrorPopup = await this.alertController.create({
      header: message,
      buttons: [
        {
          text: 'OK',
          htmlAttributes: {
            'aria-label': 'close',
          },
        },
      ],
    });
    showErrorPopup.present();
  }


  getProfilePic(base64Image: any){
    return "data:image/jpeg;base64," + base64Image;
  }

}
