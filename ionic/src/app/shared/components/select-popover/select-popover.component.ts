import { Component, Input, OnInit } from '@angular/core';
import { PopoverController } from '@ionic/angular';
import {FormBuilder, FormGroup} from "@angular/forms";
import {university} from "../../model/constants";

@Component({
  selector: 'app-select-popover',
  templateUrl: './select-popover.component.html',
  styleUrls: ['./select-popover.component.css']
})
export class SelectPopoverComponent implements OnInit {

  @Input() title: string = ""
  @Input() items: university[] = [];
  public searchUniversityForm: FormGroup = new FormGroup({});

  constructor(
    private popoverController: PopoverController,
    private formBuilder: FormBuilder
     ) { }

  ngOnInit() {
    this.searchUniversityForm = this.formBuilder.group({
      searchTerm: ['']
    });

    this.searchUniversityForm.get('searchTerm')?.valueChanges.subscribe(res => {
      this.items = this.items.filter((eachUni: university) => eachUni.name.includes(res));
    })
  }

  selectItem(item: university) {
    this.popoverController.dismiss({
      'selectedItem': item?.name
    });
  }

}
