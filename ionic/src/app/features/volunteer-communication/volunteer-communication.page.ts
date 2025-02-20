import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'volunteer-communication',
  templateUrl: './volunteer-communication.page.html',
  styleUrls: ['./volunteer-communication.page.scss'],
})
export class VolunteerCommunicationPage implements OnInit {

  constructor(private router: Router) {}

  ngOnInit() {
    
  }
  
}
