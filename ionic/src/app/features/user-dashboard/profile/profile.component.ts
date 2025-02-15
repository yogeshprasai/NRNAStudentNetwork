import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertController, LoadingController, Platform, PopoverController} from '@ionic/angular';
import {Profile} from 'src/app/shared/model/profile';
import {AuthService} from 'src/app/shared/service/auth.service';
import {ProfileAddressService} from 'src/app/shared/service/profile-address.service';
import {StatesList} from 'src/app/shared/validation';
import {Camera, CameraResultType, CameraSource} from '@capacitor/camera';
import {finalize} from "rxjs";
import {universities} from "../../../../assets/json/world_universities_and_domains";
import {SelectPopoverComponent} from "../../../shared/components/select-popover/select-popover.component";
import {university} from "../../../shared/model/constants";

@Component({
  selector: 'nrna-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  public statesList = StatesList;

  //public World_UniversityList = World_University;

  public profileForm: FormGroup = new FormGroup({});
  public errorMessage: boolean = false;
  public profileUpdateSuccess: boolean = false;
  public successMessageWithLogout: boolean = false;
  public profileValues: Profile = <Profile>{};
  public profilePicture: any = null;
  public usaUniversityList: university[] = []
  public filteredUniversities: university[] = [];
  public dataReturned: any = null;
  public memo: any = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private profileAddressService: ProfileAddressService,
    public fb: FormBuilder,
    public route: ActivatedRoute,
    private loadingCtrl: LoadingController,
    private alertController: AlertController,
    private platForm: Platform,
    private popoverController: PopoverController,


  ) {}

  async ngOnInit(){
    this.profileForm = this.fb.group({
      firstName: ['', [Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(32), Validators.pattern('^[a-zA-Z ]+$')])]],
      middleName: ['', [Validators.compose([Validators.minLength(1), Validators.maxLength(32), Validators.pattern('^[a-zA-Z ]+$')])]],
      lastName: ['', [Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(32), Validators.pattern('^[a-zA-Z ]+$')])]],
      email: ['', [Validators.compose([Validators.required, Validators.maxLength(50), Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/)])]],
      phoneNumber: ['', [Validators.compose([Validators.required, Validators.minLength(10), Validators.maxLength(16), Validators.pattern(/^[0-9\s]*$/)])]],
      showPhoneNumber: [''],
      isHelper: [''],
      isStudent: [''],
      university: [''],
      profilePicture: ['']
    });
  }

  ionViewWillEnter() {
    this.route.data.subscribe(profileResponse => {
      this.profileValues = profileResponse['profile'];
      if(this.profileValues){
        this.profileForm.get('firstName')?.patchValue(this.profileValues.firstName);
        this.profileForm.get('middleName')?.patchValue(this.profileValues.middleName);
        this.profileForm.get('lastName')?.patchValue(this.profileValues.lastName);
        this.profileForm.get('email')?.patchValue(this.profileValues.email);
        this.profileForm.get('phoneNumber')?.patchValue(this.profileValues.phoneNumber);
        this.profileForm.get('showPhoneNumber')?.patchValue(this.profileValues.showPhoneNumber);
        this.profileForm.get('isHelper')?.patchValue(this.profileValues.isHelper);
        this.profileForm.get('isStudent')?.patchValue(this.profileValues.isStudent);
        this.profileForm.get('university')?.patchValue(this.profileValues.university);
        if(this.profileValues.profilePicture){
          this.profilePicture = "data:image/jpeg;base64," + this.profileValues.profilePicture;
        }
      }
    });
    this.usaUniversityList = JSON.parse(JSON.stringify(universities)).filter((val: university) => val.country === 'United States');
    // this.profileForm.get('university')?.valueChanges.subscribe(value =>{
    //   console.log(value);
    //   const ok = this.usaUniversityList.filter(val => val.name.toLowerCase().includes(value.toLowerCase()));
    //   console.log(ok);
    // });
  }

  handleUniversitySearch(event: any){
    const query = event.target.value.toLowerCase();
    this.filteredUniversities = this.usaUniversityList.filter((d) => d.name.toLowerCase().indexOf(query) > -1);
  }

  async uploadFromCameraOrGallery() {
    let isLocal = document.URL.startsWith('http://localhost:42');
    const image = await Camera.getPhoto({
      quality: 90,
      allowEditing: false,
      resultType: CameraResultType.Base64,
      source: isLocal ? CameraSource.Photos : CameraSource.Prompt // Camera, Photos or Prompt!
    });

    if (image) {
      const base64String = `${image.base64String}`;
      this.uploadData(base64String);
    }
  }

  // Upload the base64String to db
  async uploadData(base64Image: any) {
    const loading = await this.loadingCtrl.create({
      message: 'Uploading image...',
    });
    await loading.present();
    this.profileAddressService.saveProfilePicture(base64Image)
        .pipe(
            finalize(() => {
              loading.dismiss();
            })
        )
        .subscribe({
          next: (res: any) => {
            if (res.message === "Success") {
              this.profilePicture = "data:image/jpeg;base64," + base64Image;
              this.profileUpdateSuccess = true;
            } else {
              this.showErrorAlert('File upload failed. Please try again.');
            }},
          error: () => {
            this.showErrorAlert("File upload failed. Please try again.");
          }
        });
  }

  async deleteImage() {
    const loading = await this.loadingCtrl.create({
      message: 'Deleting Profile Picture...'
    });
    await loading.present();
    this.profileAddressService.deleteProfilePicture().pipe(
        finalize(() => {
          loading.dismiss();
          this.profileUpdateSuccess = true;
        })
    ).subscribe({
        next: res => {
          console.log('HTTP response', res);
          this.profilePicture = null;
        },
        error: () => {
          this.showErrorAlert("Error! Please Try Again.");
        }
    });
  }

  async submitProfileForm(){
    const loading = await this.loadingCtrl.create({
      message: 'Updating Profile...',
    });

    this.profileForm.controls['firstName'].markAsTouched();
    this.profileForm.controls['lastName'].markAsTouched();
    this.profileForm.controls['email'].markAsTouched();
    this.profileForm.controls['phoneNumber'].markAsTouched();

    console.log(this.profileForm.controls['isStudent']?.value);
    console.log(this.profileForm.get('university')?.value?.length);
    if(this.profileForm.controls['isStudent']?.value && !this.profileForm.get('university')?.value){
      this.profileForm.controls['university'].markAsTouched();
      this.profileForm.controls['university']?.setErrors({'required': true});
      console.log(this.profileForm);
      return;
    }

    if(!this.profileForm.controls['firstName'].errors && !this.profileForm.controls['middleName'].errors && 
          !this.profileForm.controls['lastName'].errors && !this.profileForm.controls['email'].errors && 
          !this.profileForm.controls['phoneNumber'].errors){
          //Submit Form if there are no errors
          if(!this.profileForm.get('isStudent')?.value){
            this.profileForm.get('university')?.patchValue("");
          }
          await loading.present();
          this.profileAddressService.updateProfile(this.profileForm.value)
              .pipe(
                  finalize(() => {
                    loading.dismiss()
                  })
              )
              .subscribe({
                  next: (response: any) => {
                    const successMessage: string = response.message;
                    if(successMessage){
                      if(successMessage === "Success"){
                        this.profileUpdateSuccess = true;
                      }else if(successMessage === "Success but Logout User"){
                        this.successMessageWithLogout = true;
                      }
                    }
                  },
                  error: ()=> {
                    //try again popup
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
    this.profileUpdateSuccess = false;
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

    if (data !== null) {
      this.profileForm.patchValue({ university: data?.selectedItem });
      this.dataReturned = data?.selectedItem;
      this.memo = this?.dataReturned + "/" + this.memo;
    }
  }

}

