import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
    selector: 'nrna-jobs',
    templateUrl: './jobs.component.html',
    styleUrl: './jobs.component.css',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class JobsComponent { }
