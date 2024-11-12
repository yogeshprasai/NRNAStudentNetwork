import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'nrna-communication',
  templateUrl: './communication.component.html',
  styleUrls: ['./communication.component.scss'],
})
export class CommunicationComponent  implements OnInit {

  private activatedRoute = inject(ActivatedRoute);
  constructor(private router: Router) { }

  ngOnInit() {}

}
