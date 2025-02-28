import { Component, OnInit } from '@angular/core';
import {SelectPopoverComponent} from "../../shared/components/select-popover/select-popover.component";
import {PopoverController} from "@ionic/angular";
import {university} from "../../shared/model/constants";
import {universities} from "../../../assets/json/world_universities_and_domains";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {MiscService} from "../../shared/service/misc.service";

@Component({
  selector: 'app-university-outreach',
  templateUrl: './university-outreach.component.html',
  styleUrls: ['./university-outreach.component.scss']
})
export class UniversityOutreachComponent implements OnInit {

  public memo: any = null;
  public dataReturned: any = null;
  public universityOutreachers: any = [];
  public usaUniversityList: university[] = [];
  public selectedUniversityOutreachers: any = [];
  public universityOutreachForm: FormGroup = new FormGroup({});

  constructor(private popoverController: PopoverController, private fb: FormBuilder, private miscService: MiscService) { }

  ngOnInit() {
    this.universityOutreachForm = this.fb.group({
        university: new FormControl("")
    })
    this.usaUniversityList = JSON.parse(JSON.stringify(universities));
    this.miscService.getUniversityOutreachList().subscribe((response: any) => {
      this.universityOutreachers = JSON.parse(JSON.stringify(response));
    });

    this.universityOutreachForm.valueChanges.subscribe(() => {
        this.selectedUniversityOutreachers = this.universityOutreachers.filter((val: any) =>
          val.associatedUniversities.includes(this.universityOutreachForm.get('university')?.value)
        )
    });
  }

  async openPopOver(ev: any) {
    const popover = await this.popoverController.create({
      component: SelectPopoverComponent,
      event: ev,
      translucent: false,
      componentProps: {
        title: "Select University",
        items: this.usaUniversityList,
      }
    });

    await popover.present();

    // Listen for onDidDismiss
    const { data } = await popover.onDidDismiss();

    if (data != null) {
      this.universityOutreachForm.patchValue({ university: data?.selectedItem });
      this.dataReturned = data?.selectedItem;
      this.memo = this?.dataReturned + "/" + this.memo;
    }
  }

}
