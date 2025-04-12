import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AboutUsComponent } from './about-us.component';
import { AboutUsRoutingModule } from './about-us.routing';
import { SharedModule } from 'src/app/shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { BoardComponent } from './board/board.component';
import { OurMissionComponent } from './our-mission/our-mission.component';
import { DevTeamComponent } from './dev-team/dev-team.component';

@NgModule({
  imports: [
    CommonModule,
    IonicModule,
    AboutUsRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
  declarations: [AboutUsComponent, OurMissionComponent, BoardComponent, DevTeamComponent]
})
export class AboutUsModule { }
