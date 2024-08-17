import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HelperService } from 'src/app/shared/service/helper.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent  implements OnInit {

  public searchForm: FormGroup = new FormGroup({});
  public listOfMembers: any = [];
  public searchedMemberList: any = [];
  private activatedRoute = inject(ActivatedRoute);
  constructor(private router: Router, private helperService: HelperService, private fb: FormBuilder) { }

  ngOnInit() {
    this.searchForm = this.fb.group({
      search: [''],
    });
    this.listOfMembers = this.helperService.getAllHelpers();
  }

  filterMemberList(){
    const text = this.searchForm.controls['search']?.value;
    this.searchedMemberList = this.listOfMembers.filter((member: any) => member.firstName.includes(text) || member.lastName.includes(text)
      || member.city.includes(text) || member.state.includes(text));
  }

  navigateTo(page: string){
    this.router.navigate(['..', page], {relativeTo: this.activatedRoute});
  }

}
