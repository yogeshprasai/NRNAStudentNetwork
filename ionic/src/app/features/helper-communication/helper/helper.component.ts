import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HelperService } from 'src/app/shared/service/helper.service';

@Component({
  selector: 'app-helper',
  templateUrl: './helper.component.html',
  styleUrls: ['./helper.component.scss'],
})
export class HelperComponent  implements OnInit {

  public folder!: string;
  private activatedRoute = inject(ActivatedRoute);
  public helpers: any = [];

  constructor(private router: Router, private helperService: HelperService) {}

  ngOnInit() {
    this.helperService.getAllHelpers().subscribe(response => {
      console.log(response);
      this.helpers = response
    })
  }

}
