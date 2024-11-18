import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';

@Component({
    selector: 'nrna-college-search',
    templateUrl: './college-search.component.html',
    styleUrl: './college-search.component.css',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CollegeSearchComponent implements OnInit, OnDestroy { 

    ngOnInit(): void {
        window.onbeforeunload = () => this.ngOnDestroy();
    }

    ngOnDestroy(): void {

    }
}
