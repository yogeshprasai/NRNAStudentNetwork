import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertController, NavController, Platform, PopoverController} from '@ionic/angular';
import {AuthService} from 'src/app/shared/service/auth.service';
import {ProfileAddressService} from 'src/app/shared/service/profile-address.service';
import {StatesList} from 'src/app/shared/validation';
import {Camera, CameraResultType, CameraSource} from '@capacitor/camera';
import {finalize} from "rxjs";
import {universities} from "../../../../assets/json/world_universities_and_domains";
import {SelectPopoverComponent} from "../../../shared/components/select-popover/select-popover.component";
import {university} from "../../../shared/model/constants";
import {NavigationService} from "../../../shared/service/navigation.service";
import {UserProfile} from "../../../shared/model/user-profile";
import {NrnaRoutes} from "../../../shared/service/constant";

@Component({
  selector: 'nrna-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  public statesList = StatesList;

  public profileForm: FormGroup = new FormGroup({});
  public errorMessage: boolean = false;
  public profilePictureUpdateSuccess: boolean = false;
  public profileUpdateSuccess: boolean = false;
  public navigateToStudentNetwork: boolean = false;
  public successMessageWithLogout: boolean = false;
  public wantToBeRemovedAsVolunteer: boolean = false;
  public originalProfileValues: UserProfile = <UserProfile>{};
  public profilePicture: any = null;
  public usaUniversityList: university[] = [];
  public dataReturned: any = null;
  public memo: any = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private profileAddressService: ProfileAddressService,
    public fb: FormBuilder,
    public route: ActivatedRoute,
    private alertController: AlertController,
    private popoverController: PopoverController,
    private navigationService: NavigationService,
    private navController: NavController
  ) {}

  ngOnInit(){
    this.profileForm = this.fb.group({
      firstName: ['', [Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(32), Validators.pattern('^[a-zA-Z ]+$')])]],
      middleName: ['', [Validators.compose([Validators.minLength(1), Validators.maxLength(32), Validators.pattern('^[a-zA-Z ]+$')])]],
      lastName: ['', [Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(32), Validators.pattern('^[a-zA-Z ]+$')])]],
      email: ['', [Validators.compose([Validators.required, Validators.maxLength(50), Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")])]],
      phoneNumber: ['', [Validators.compose([Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern(/^[0-9\s]*$/)])]],
      showPhoneNumber: [''],
      isApplyForVolunteer: [''],
      isVolunteer: [''],
      isStudent: [''],
      university: [''],
      profilePicture: [''],
      city: ['', [Validators.compose([Validators.required, Validators.maxLength(32), Validators.pattern("^[a-zA-Z ]*$")])]],
      state: ['', [Validators.compose([Validators.required, Validators.pattern("^[a-zA-Z ]*$")])]],
      zipCode: ['', [Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(5), Validators.pattern("^[0-9]*$")])]]
    });
    this.usaUniversityList = JSON.parse(JSON.stringify(universities));
  }

  ionViewWillEnter() {
    this.route.data.subscribe((profileResponse: any) => {
      if(profileResponse.profile.isAdmin){
        this.navigationService.reArrangeMenuItem(true);
      }
      this.originalProfileValues = profileResponse['profile'];
      if(this.originalProfileValues){
        this.profileForm.get('firstName')?.patchValue(this.originalProfileValues.firstName);
        this.profileForm.get('middleName')?.patchValue(this.originalProfileValues.middleName);
        this.profileForm.get('lastName')?.patchValue(this.originalProfileValues.lastName);
        this.profileForm.get('email')?.patchValue(this.originalProfileValues.email);
        this.profileForm.get('phoneNumber')?.patchValue(this.originalProfileValues.phoneNumber);
        this.profileForm.get('showPhoneNumber')?.patchValue(this.originalProfileValues.showPhoneNumber);
        this.profileForm.get('isApplyForVolunteer')?.patchValue(this.originalProfileValues.isApplyForVolunteer);
        this.profileForm.get('isVolunteer')?.patchValue(this.originalProfileValues.isVolunteer);
        this.profileForm.get('isStudent')?.patchValue(this.originalProfileValues.isStudent);
        this.profileForm.get('university')?.patchValue(this.originalProfileValues.university);
        this.profileForm.get('city')?.patchValue(this.originalProfileValues.city);
        this.profileForm.get('state')?.patchValue(this.originalProfileValues.state);
        this.profileForm.get('zipCode')?.patchValue(this.originalProfileValues.zipCode);
        if(this.originalProfileValues.profilePicture){
          this.profilePicture = "data:image/jpeg;base64," + this.originalProfileValues.profilePicture;
          this.profileForm.get('profilePicture')?.patchValue(this.profilePicture);
        }
      }
    });
    this.usaUniversityList = JSON.parse(JSON.stringify(universities));

    this.profileForm.get('isVolunteer')?.valueChanges.subscribe((val: boolean) => {
      if(this.originalProfileValues.isVolunteer){
        if(!val){
          this.wantToBeRemovedAsVolunteer = true;
        }
      }else{
        this.wantToBeRemovedAsVolunteer = false;
      }
    });

    this.profileForm.get('isStudent')?.valueChanges.subscribe((val: boolean) => {
      if(!val){
        this.profileForm.get('university')?.patchValue(null);
      }
    })
  }

  async uploadFromCameraOrGallery() {
    let isLocal = document.URL.startsWith('http://localhost:42');
    const image = await Camera.getPhoto({
      quality: 100,
      allowEditing: true,
      resultType: CameraResultType.Base64,
      source: isLocal ? CameraSource.Photos : CameraSource.Prompt // Camera, Photos or Prompt!
    });

    if (image) {
      const base64String = `${image.base64String}`;
      this.uploadData(base64String);
    }else{
      alert("No Image");
    }
  }

  // Upload the base64String to db
  async uploadData(base64Image: any) {
    console.log(base64Image)
    this.profileAddressService.saveProfilePicture(base64Image)
        .pipe(
            finalize(() => {
              this.profileForm.controls['profilePicture'].setErrors(null);
              this.profileForm.controls['profilePicture'].patchValue(base64Image);
            })
        )
        .subscribe({
          next: (res: any) => {
            if (res.message === "Success") {
              this.profilePicture = "data:image/jpeg;base64," + base64Image;
              this.profilePictureUpdateSuccess = true;
            } else {
              this.showErrorAlert('File upload failed. Please try again.');
            }},
          error: () => {
            this.showErrorAlert("File upload failed. Decrease picture size to less than 1 MB.");
          }
        });
  }

  async deleteImage() {
    this.profileAddressService.deleteProfilePicture().pipe(
        finalize(() => {
          this.profileForm.get('profilePicture')?.patchValue(null);
          this.profilePictureUpdateSuccess = true;
        })
    ).subscribe({
        next: res => {
          this.profilePicture = null;
        },
        error: () => {
          this.showErrorAlert("Error! Please Try Again.");
        }
    });
  }

  async submitProfileForm(){
    this.profileForm.controls['firstName'].markAsTouched();
    this.profileForm.controls['lastName'].markAsTouched();
    this.profileForm.controls['email'].markAsTouched();
    this.profileForm.controls['phoneNumber'].markAsTouched();
    this.profileForm.controls['city'].markAsTouched();
    this.profileForm.controls['state'].markAsTouched();
    this.profileForm.controls['zipCode'].markAsTouched();

    this.profileForm.controls['firstName'].setValue(this.profileForm.controls['firstName'].value.trim());
    this.profileForm.controls['lastName'].setValue(this.profileForm.controls['lastName'].value.trim());
    this.profileForm.controls['email'].setValue(this.profileForm.controls['email'].value.trim());
    this.profileForm.controls['phoneNumber'].setValue(this.profileForm.controls['phoneNumber'].value.trim());
    this.profileForm.controls['city'].setValue(this.profileForm.controls['city'].value.trim());
    this.profileForm.controls['state'].setValue(this.profileForm.controls['state'].value.trim());
    this.profileForm.controls['zipCode'].setValue(this.profileForm.controls['zipCode'].value.trim());

    if(this.profileForm.controls['isStudent']?.value &&
        (this.profileForm.get('university')?.value == null || this.profileForm.get('university')?.value === "")){
      this.profileForm.controls['university'].markAsTouched();
      this.profileForm.controls['university']?.setErrors({'required': true});
      return;
    }

    if(!this.profileForm.controls['firstName'].errors && !this.profileForm.controls['middleName'].errors && 
          !this.profileForm.controls['lastName'].errors && !this.profileForm.controls['email'].errors && 
          !this.profileForm.controls['phoneNumber'].errors && !this.profileForm.controls['city'].errors
          && !this.profileForm.controls['state'].errors && !this.profileForm.controls['zipCode'].errors){
          //Submit Form if there are no errors
          if(!this.profileForm.get('isStudent')?.value){
            this.profileForm.get('university')?.patchValue("");
          }
          if(this.profileForm.status === 'INVALID'){
            return;
          }

          //Capitalize Names
          if(!this.profileForm.controls['firstName'].errors){
            const firstName = this.profileForm.controls['firstName'].value;
            if(firstName){
              const capitalized = firstName.charAt(0).toUpperCase() + firstName.slice(1);
              this.profileForm.get('firstName')?.patchValue(capitalized);
            }
          }

          if(!this.profileForm.controls['middleName'].errors){
            const middleName = this.profileForm.controls['middleName'].value;
            if(middleName){
              const capitalized = middleName.charAt(0).toUpperCase() + middleName.slice(1);
              this.profileForm.get('middleName')?.patchValue(capitalized);
            }

          }

          if(!this.profileForm.controls['lastName'].errors){
            const lastName = this.profileForm.controls['lastName'].value;
            if(lastName){
              const capitalized = lastName.charAt(0).toUpperCase() + lastName.slice(1);
              this.profileForm.get('lastName')?.patchValue(capitalized);
            }
          }

          this.profileAddressService.updateProfile(this.profileForm.value)
              .subscribe({
                  next: (response: any) => {
                    const successMessage: string = response.message;
                    if(successMessage){
                      //To change the visual look of isVolunteer and isApplyForVolunteer
                      if(this.originalProfileValues.isVolunteer && !this.profileForm.get('isVolunteer')?.value){
                        this.originalProfileValues.isVolunteer = false;
                        this.profileForm.get('isVolunteer')?.setValue(false);
                      }
                      if(successMessage === "Success"){
                        this.profileUpdateSuccess = true;
                      }else if(successMessage === "Success but Logout User"){
                        this.successMessageWithLogout = true;
                      }
                    }
                  },
                  error: ()=> {
                    this.showErrorAlert("Error! Please Try Again.");
                  }
              });
    }
  }

  //Error Popup
  async showErrorAlert(message: string){
    const showErrorPopup = await this.alertController.create({
      header: message,
      buttons: [
        {
          text: 'OK',
          htmlAttributes: {
            'aria-label': 'close',
          },
        },
      ],
    });
    showErrorPopup.present();
  }
  

  private resetButtons(){
    this.errorMessage = false;
    this.profilePictureUpdateSuccess = false;
    this.profileUpdateSuccess = false;
    this.successMessageWithLogout = false;
    this.wantToBeRemovedAsVolunteer = false;
    this.navigateToStudentNetwork = false;
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

  public cancelOkButtonsForVolunteer = [
    {
      text: 'Cancel',
      role: 'confirm',
      handler: () => {
          this.profileForm.get('isVolunteer')?.setValue(true);
          this.profileForm.updateValueAndValidity();
          this.resetButtons();
      },
    },
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.resetButtons();
      },
    },
  ];

  public navigateToStudentNetworkButtons = [
    {
      text: 'Cancel',
      role: 'confirm',
      handler: () => {
        this.resetButtons();
      },
    },
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.resetButtons();
        this.router.navigate([NrnaRoutes.Student]);
      },
    },
  ];

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
      this.profileForm.patchValue({ university: data?.selectedItem });
      this.dataReturned = data?.selectedItem;
      this.memo = this?.dataReturned + "/" + this.memo;
    }
  }

  backButton() {
    this.navController.back();
  }

  goBack(){

  }

  navigateTo(){
    this.navigateToStudentNetwork = true;
  }
}

