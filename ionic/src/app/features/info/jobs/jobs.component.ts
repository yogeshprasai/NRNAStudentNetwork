import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
    selector: 'nrna-jobs',
    templateUrl: './jobs.component.html',
    styleUrl: './jobs.component.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class JobsComponent {
    readonly nrnaEmail = "students@nrnusa.org";

    mailTo(){
        window.location.href = 'mailto:' + this.nrnaEmail;
    }
}
