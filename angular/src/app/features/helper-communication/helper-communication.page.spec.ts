import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { HelperCommunicationPage } from './helper-communication.page';

describe('FolderPage', () => {
  let component: HelperCommunicationPage;
  let fixture: ComponentFixture<HelperCommunicationPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HelperCommunicationPage],
      imports: [IonicModule.forRoot(), RouterModule.forRoot([])]
    }).compileComponents();

    fixture = TestBed.createComponent(HelperCommunicationPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
