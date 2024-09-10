import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertController } from '@ionic/angular';
import { AuthService } from 'src/app/shared/service/auth.service';
import { ProfileAddressService } from 'src/app/shared/service/profile-address.service';
import { StatesList } from 'src/app/shared/validation';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss'],
})
export class AddressComponent  implements OnInit {

  public statesList = StatesList;

  public addressForm: FormGroup = new FormGroup({});

  constructor(
    private authService: AuthService,
    private router: Router,
    private profileAddressService: ProfileAddressService,
    private alertCtrl: AlertController,
    public fb: FormBuilder
  ) {}

  ngOnInit(){
    this.addressForm = this.fb.group({
      addressLine1: ['2336 Montauk Dr', [Validators.compose([Validators.required, Validators.maxLength(32), Validators.pattern("^[a-zA-Z0-9.,#:&apos;&quot; ]*$")])]],
      addressLine2: ['Suite 101', [Validators.compose([Validators.maxLength(32), Validators.pattern("^[a-zA-Z0-9.,#:&apos;&quot; ]*$")])]],
      city: ['Crofton', [Validators.compose([Validators.required, Validators.maxLength(32), Validators.pattern("^[a-zA-Z ]*$")])]],
      state: ['Maryland', [Validators.compose([Validators.required, Validators.pattern("^[a-zA-Z ]*$")])]],
      zipCode: ['21114', [Validators.compose([Validators.required, Validators.maxLength(10), Validators.pattern("^[0-9]*$")])]]
    });
  }

  ionViewWillEnter() {
    
  }

  async logOut(): Promise<void> {
    await this.authService.logout();
    this.router.navigateByUrl('login');
  }

  submitAddressForm(){
    this.addressForm.controls['addressLine1'].markAsTouched();
    this.addressForm.controls['city'].markAsTouched();
    this.addressForm.controls['state'].markAsTouched();
    this.addressForm.controls['zipCode'].markAsTouched();

    if(!this.addressForm.controls['addressLine1'].errors && !this.addressForm.controls['addressLine2'].errors && 
          !this.addressForm.controls['city'].errors && !this.addressForm.controls['state'].errors && 
          !this.addressForm.controls['zipCode'].errors){
          //Submit Form
          console.log(this.addressForm.value);
          this.profileAddressService.saveOrUpdateAddress(this.addressForm.value).subscribe(values => {
            console.log(values);
          })
    }

  }

}
