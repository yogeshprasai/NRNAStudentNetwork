import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {UsersService} from "../../../shared/service/users.service";

@Component({
  selector: 'nrna-helper',
  templateUrl: './helper.component.html',
  styleUrls: ['./helper.component.scss'],
})
export class HelperComponent implements OnInit {

  public folder!: string;
  private activatedRoute = inject(ActivatedRoute);
  public helpers: any = [];

  constructor(private route: ActivatedRoute, private usersService: UsersService) {

  }

  ngOnInit() {

  }

  updateHelperInfo(){
    //console.log(this.route);
    this.route?.data.subscribe((response: any) => {
      if(response.allHelpers){
        this.helpers = response.allHelpers.filter((helper: any) => helper.isHelper);
      }
    });
  }

  ionViewWillEnter() {
    this.updateHelperInfo();
  }

  ionViewDidEnter(){
    this.updateHelperInfo();
  }

  getProfilePic(base64String: string): string{
    return "data:image/jpeg;base64," + base64String;
  }

}
