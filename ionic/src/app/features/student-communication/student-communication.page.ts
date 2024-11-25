import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'nrna-student',
  templateUrl: './student-communication.page.html',
  styleUrls: ['./student-communication.page.scss'],
})
export class StudentCommunicationPage implements OnInit {

  constructor(private router: Router) {}

  ngOnInit() {
    
  }
  
}
