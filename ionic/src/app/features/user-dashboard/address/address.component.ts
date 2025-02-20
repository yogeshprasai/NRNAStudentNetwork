import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertController, IonRouterOutlet, LoadingController, NavController, ToastController} from '@ionic/angular';
import { AuthService } from 'src/app/shared/service/auth.service';
import { ProfileAddressService } from 'src/app/shared/service/profile-address.service';
import { StatesList } from 'src/app/shared/validation';
import {Address} from "../../../shared/model/address";
import {finalize} from "rxjs";

@Component({
  selector: 'nrna-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss'],
})
export class AddressComponent  implements OnInit {

  public statesList = StatesList;

  public addressForm: FormGroup = new FormGroup({});
  public addressValues: Address = <Address>{};

  constructor(
    private authService: AuthService,
    private router: Router,
    private profileAddressService: ProfileAddressService,
    public fb: FormBuilder,
    private route: ActivatedRoute,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController,
    private alertController: AlertController,
    private navController: NavController,
    private routerOutlet: IonRouterOutlet
  ) {}

  ngOnInit(){
    this.addressForm = this.fb.group({
      addressLine1: ['', [Validators.compose([Validators.maxLength(32), Validators.pattern("^[a-zA-Z0-9.,#:&apos;&quot; ]*$")])]],
      addressLine2: ['', [Validators.compose([Validators.pattern("^[a-zA-Z0-9.,#:&apos;&quot; ]*$")])]],
      city: ['', [Validators.compose([Validators.required, Validators.maxLength(32), Validators.pattern("^[a-zA-Z ]*$")])]],
      state: ['', [Validators.compose([Validators.required, Validators.pattern("^[a-zA-Z ]*$")])]],
      zipCode: ['', [Validators.compose([Validators.required, Validators.maxLength(10), Validators.pattern("^[0-9]*$")])]]
    });
  }

  ionViewWillEnter() {
    this.route.data.subscribe(addressResponse => {
      this.addressValues = addressResponse['address'];
      if(this.addressValues){
        this.addressForm.get('addressLine1')?.patchValue(this.addressValues.addressLine1);
        this.addressForm.get('addressLine2')?.patchValue(this.addressValues.addressLine2);
        this.addressForm.get('city')?.patchValue(this.addressValues.city);
        this.addressForm.get('state')?.patchValue(this.addressValues.state);
        this.addressForm.get('zipCode')?.patchValue(this.addressValues.zipCode);
      }
    });
  }

  async logOut(): Promise<void> {
    await this.authService.logout();
    this.router.navigateByUrl('login');
  }

  async submitAddressForm(){
    const loading = await this.loadingCtrl.create({
      message: 'Updating Profile...',
    });

    this.addressForm.controls['addressLine1'].markAsTouched();
    this.addressForm.controls['city'].markAsTouched();
    this.addressForm.controls['state'].markAsTouched();
    this.addressForm.controls['zipCode'].markAsTouched();

    if(!this.addressForm.controls['addressLine1'].errors && !this.addressForm.controls['addressLine2'].errors && 
          !this.addressForm.controls['city'].errors && !this.addressForm.controls['state'].errors && 
          !this.addressForm.controls['zipCode'].errors){
          //Submit Form
          this.profileAddressService.saveOrUpdateAddress(this.addressForm.value)
              .pipe(
                  finalize(() => {
                    loading.dismiss()
                  })
              )
              .subscribe({
                next: response => {
                  if(response.message.includes("Success")){
                    this.presentToast("Address Update Complete.")
                  }
                },
                error: () => {
                  this.showErrorAlert("Error! Please Try Again.");
                }
              });
    }else {
      loading.dismiss();
    }
  }

  async presentToast(text: any) {
    const toast = await this.toastCtrl.create({
      message: text,
      duration: 3000
    });
    toast.present();
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


}
