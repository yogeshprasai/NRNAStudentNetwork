import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { VolunteerCommunicationPage } from './volunteer-communication.page';

describe('FolderPage', () => {
  let component: VolunteerCommunicationPage;
  let fixture: ComponentFixture<VolunteerCommunicationPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VolunteerCommunicationPage],
      imports: [IonicModule.forRoot(), RouterModule.forRoot([])]
    }).compileComponents();

    fixture = TestBed.createComponent(VolunteerCommunicationPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
