import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UsersService} from 'src/app/shared/service/users.service';

@Component({
  selector: 'nrna-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent  implements OnInit {

  public searchForm: FormGroup = new FormGroup({});
  public allStudents: any = [];
  public studentsFilteredList: any = [];
  private activatedRoute = inject(ActivatedRoute);
  constructor(private router: Router, private usersService: UsersService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.searchForm = this.fb.group({
      search: [''],
    });
    this.getStudents();
  }

  ionViewWillEnter(): void{
    this.getStudents();
  }

  getStudents(): void{
    this.activatedRoute.data.subscribe((response: any) => {
      console.log(response.allStudents);
      this.allStudents = response.allStudents.filter((student: any) => student.isStudent);
      if(this.allStudents){
        this.studentsFilteredList = this.allStudents;
      }
    })
  }

  filterStudentsList(){
    const text = this.searchForm.controls['search']?.value;
    this.studentsFilteredList = this.allStudents.filter((member: any) => {
      const firstName: string = member.firstName && member.firstName.toLowerCase().includes(text.toLowerCase());
      const lastName: string = member.lastName && member.lastName.toLowerCase().includes(text.toLowerCase());
      const city: string = member.userAddress && member.userAddress.city && member.userAddress.city.toLowerCase().includes(text.toLowerCase());
      const state: string = member.userAddress && member.userAddress.state && member.userAddress.state.toLowerCase().includes(text.toLowerCase());
      const zipCode: number = member.userAddress && member.userAddress.zipCode && member.userAddress.zipCode && member.userAddress.zipCode.toLowerCase().includes(text.toLowerCase());
      const university: string = member.university && member.university.toLowerCase().includes(text.toLowerCase())
      return firstName || lastName || city || state || zipCode || university;
    });
  }

  navigateTo(page: string){
    this.router.navigate(['..', page], {relativeTo: this.activatedRoute});
  }

}
