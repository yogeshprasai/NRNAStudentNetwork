import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'nrna-about-us',
  templateUrl: './useful-info.component.html',
  styleUrls: ['./useful-info.component.scss'],
})
export class UsefulInfoComponent implements OnInit {

  readonly nrnUSAEmail: string = "info@nrnusa.org";

  constructor() { }

  ngOnInit() {}

}
