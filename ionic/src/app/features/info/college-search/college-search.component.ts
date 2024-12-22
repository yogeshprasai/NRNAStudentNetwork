import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {University} from "../../../shared/model/constants";

@Component({
    selector: 'nrna-college-search',
    templateUrl: './college-search.component.html',
    styleUrl: './college-search.component.css',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CollegeSearchComponent implements OnInit, OnDestroy {

    public universities: University[] = [];

    constructor(public route: ActivatedRoute) {
    }

    ngOnInit(): void {
        window.onbeforeunload = () => this.ngOnDestroy();
        this.route.data.subscribe((response: any) => {
            if(response.topUniversities){
                this.universities = response.topUniversities;
            }
        })
    }

    ngOnDestroy(): void {

    }

    ionViewWillEnter() {

    }
}
