import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HelperService } from 'src/app/shared/service/helper.service';

@Component({
  selector: 'nrna-helper',
  templateUrl: './helper.component.html',
  styleUrls: ['./helper.component.scss'],
})
export class HelperComponent  implements OnInit {

  public folder!: string;
  private activatedRoute = inject(ActivatedRoute);
  public helpers: any = [];

  constructor(private router: Router, private helperService: HelperService) {
    this.updateHelperInfo();
  }

  ngOnInit() {

  }

  updateHelperInfo(){
    this.helperService.getAllHelpers().subscribe(response => {
      console.log(response);
      this.helpers = response;
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
