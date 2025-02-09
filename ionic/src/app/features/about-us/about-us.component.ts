import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'nrna-about-us',
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.scss'],
})
export class AboutUsComponent  implements OnInit {

  readonly nrnaEmail = "students@nrnusa.org";

  constructor() { }

  ngOnInit() {}

}
