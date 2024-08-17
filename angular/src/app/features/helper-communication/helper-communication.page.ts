import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'helper-communication',
  templateUrl: './helper-communication.page.html',
  styleUrls: ['./helper-communication.page.scss'],
})
export class HelperCommunicationPage implements OnInit {

  constructor(private router: Router) {}

  ngOnInit() {
    
  }
  
}
