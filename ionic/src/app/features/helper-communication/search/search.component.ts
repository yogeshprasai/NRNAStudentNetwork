import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HelperService } from 'src/app/shared/service/helper.service';

@Component({
  selector: 'nrna-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent  implements OnInit {

  public searchForm: FormGroup = new FormGroup({});
  public allHelpers: any = [];
  public helpersFilteredList: any = [];
  private activatedRoute = inject(ActivatedRoute);
  constructor(private router: Router, private helperService: HelperService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.searchForm = this.fb.group({
      search: [''],
    });
    this.getHelpers();
  }

  ionViewWillEnter(): void{
    this.getHelpers();
  }

  getHelpers(): void{
    this.helperService.getAllHelpers().subscribe(response => {
      this.allHelpers = response
      this.helpersFilteredList = this.allHelpers;
    })
  }

  filterHelperList(){
    const text = this.searchForm.controls['search']?.value;
    this.helpersFilteredList = this.allHelpers.filter((member: any) => {
      const firstName: string = member.firstName && member.firstName.toLowerCase().includes(text.toLowerCase());
      const lastName: string = member.lastName && member.lastName.toLowerCase().includes(text.toLowerCase());
      const city: string = member.userAddress && member.userAddress.city && member.userAddress.city.toLowerCase().includes(text.toLowerCase());
      const state: string = member.userAddress && member.userAddress.state && member.userAddress.state.toLowerCase().includes(text.toLowerCase());
      const zipCode: number = member.userAddress.zipCode && member.userAddress.zipCode && member.userAddress.zipCode.toLowerCase().includes(text.toLowerCase());
      return firstName || lastName || city || state || zipCode;
    });
  }

  navigateTo(page: string){
    this.router.navigate(['..', page], {relativeTo: this.activatedRoute});
  }

}
