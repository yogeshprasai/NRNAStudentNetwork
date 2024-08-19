import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertController } from '@ionic/angular';
import { UserProfile } from 'src/app/shared/model/constants';
import { AuthService } from 'src/app/shared/service/auth.service';
import { ProfileService } from 'src/app/shared/service/profile.service';
import { StatesList } from 'src/app/shared/validation';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  public statesList = StatesList;

  public profileForm: FormGroup = new FormGroup({});
  public successMessage: boolean = false;
  public successMessageWithLogout: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private profileService: ProfileService,
    private alertCtrl: AlertController,
    public fb: FormBuilder
  ) {}

  ngOnInit(){
    this.profileForm = this.fb.group({
      firstName: ['Yogesh', [Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(32), Validators.pattern('^[a-zA-Z ]+$')])]],
      middleName: ['Kumar', [Validators.compose([Validators.minLength(1), Validators.maxLength(32), Validators.pattern('^[a-zA-Z ]+$')])]],
      lastName: ['Prasai', [Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(32), Validators.pattern('^[a-zA-Z ]+$')])]],
      email: ['yogesh200@hotmail.com', [Validators.compose([Validators.required, Validators.maxLength(50), Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/)])]],
      phoneNumber: ['2409936466', [Validators.compose([Validators.required, Validators.minLength(10), Validators.maxLength(16), Validators.pattern(/^[0-9\s]*$/)])]]
    });
  }

  ionViewWillEnter() {
    
  }

  async logOut(): Promise<void> {
    await this.authService.logout();
    this.router.navigateByUrl('login');
  }

  submitProfileForm(){
    this.profileForm.controls['firstName'].markAsTouched();
    this.profileForm.controls['lastName'].markAsTouched();
    this.profileForm.controls['email'].markAsTouched();
    this.profileForm.controls['phoneNumber'].markAsTouched();

    if(!this.profileForm.controls['firstName'].errors && !this.profileForm.controls['middleName'].errors && 
          !this.profileForm.controls['lastName'].errors && !this.profileForm.controls['email'].errors && 
          !this.profileForm.controls['phoneNumber'].errors){
          //Submit Form if there are no errors
          this.profileService.updateProfile(this.profileForm.value).subscribe(response =>{
            const successMessage = response.message;
            if(successMessage){
              if(successMessage === "Success"){
                this.successMessage = true;
              }else if(successMessage === "Success but Logout User"){
                this.successMessageWithLogout = true;
              }
            }
          });
    }
  }

  private resetButtons(){
    this.successMessage = false;
    this.successMessageWithLogout = false;
  }

  public successButtons = [
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.resetButtons();
      },
    },
  ];

  public successButtonsWithLogout = [
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.resetButtons();
        this.authService.logout().subscribe(() => {
          this.authService.removeUserAndToken();
          this.router.navigate(['auth/sign-in']);
        });
      },
    },
  ];
}
