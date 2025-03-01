import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UsersService} from 'src/app/shared/service/users.service';
import {AuthService} from "../../../shared/service/auth.service";
import {AlertController} from "@ionic/angular";

@Component({
  selector: 'nrna-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss'],
})
export class StudentComponent implements OnInit {

  public folder!: string;
  private activatedRoute = inject(ActivatedRoute);
  public students: any = [];

  constructor(private route: ActivatedRoute, private usersService: UsersService,
              private authService: AuthService, private alertController: AlertController) {

  }

  ngOnInit() {

  }

  updateStudentsInfo(){
    this.route?.data.subscribe((response: any) => {
      if(response.allStudents){
        this.students = response.allStudents;
      }else{
        this.students = null;
      }
    });
  }

  ionViewWillEnter() {
    this.updateStudentsInfo();
  }

  getProfilePic(base64String: string): string{
    return "data:image/jpeg;base64," + base64String;
  }

}
