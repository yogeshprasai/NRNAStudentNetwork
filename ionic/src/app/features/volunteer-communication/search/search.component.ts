import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {UsersService} from "../../../shared/service/users.service";
import {ViewWillEnter} from "@ionic/angular";

@Component({
  selector: 'nrna-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent  implements OnInit, ViewWillEnter {

  public searchForm: FormGroup = new FormGroup({});
  public allVolunteers: any = [];
  public volunteersFilteredList: any = [];
  public errorWhileRetrievingVolunteers: boolean = false;
  private activatedRoute = inject(ActivatedRoute);
  constructor(private router: Router, private route: ActivatedRoute, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.searchForm = this.fb.group({
      search: [''],
    });
    this.getVolunteers();
  }

  ionViewWillEnter(): void{
    this.searchForm.get('search')?.reset();
    this.getVolunteers();
  }

  getVolunteers(){
    this.route?.data.subscribe((response: any) => {
      if(response.allVolunteers){
        this.allVolunteers = response.allVolunteers;
        console.log(this.allVolunteers);
        this.volunteersFilteredList = this.allVolunteers;
      }
    });
  }

  filterVolunteerList(){
    const text = this.searchForm.controls['search']?.value;
    this.volunteersFilteredList = this.allVolunteers.filter((member: any) => {
      const firstName: string = member.firstName && member.firstName.toLowerCase().includes(text.toLowerCase());
      const lastName: string = member.lastName && member.lastName.toLowerCase().includes(text.toLowerCase());
      const city: string = member.userAddress && member.userAddress.city && member.userAddress.city.toLowerCase().includes(text.toLowerCase());
      const state: string = member.userAddress && member.userAddress.state && member.userAddress.state.toLowerCase().includes(text.toLowerCase());
      const zipCode: number = member.userAddress && member.userAddress.zipCode && member.userAddress.zipCode && member.userAddress.zipCode.toLowerCase().includes(text.toLowerCase());
      return firstName || lastName || city || state || zipCode;
    });
  }

  navigateTo(page: string){
    this.router.navigate(['..', page], {relativeTo: this.activatedRoute});
  }

}
