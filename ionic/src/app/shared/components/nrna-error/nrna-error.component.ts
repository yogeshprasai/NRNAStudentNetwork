import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import {
  Validation_AddressPage,
  Validation_Login,
  Validation_Password_Reset_Send, Validation_Password_Reset_Verify,
  Validation_ProfilePage,
  Validation_SignUp
} from '../../validation';

@Component({
  selector: 'nrna-error',
  templateUrl: './nrna-error.component.html',
  styleUrls: ['./nrna-error.component.scss'],
})
export class NrnaErrorComponent  implements OnInit {

  readonly validationSignUpPage: any = Validation_SignUp;
  readonly validataionLoginPage: any = Validation_Login;
  readonly validationPasswordResetSendPage: any = Validation_Password_Reset_Send;
  readonly validataionPasswordResetVerifyPage: any = Validation_Password_Reset_Verify;
  readonly validationProfilePage: any = Validation_ProfilePage;
  readonly validationAddressPage: any = Validation_AddressPage;

  @Input('form') form: FormGroup = new FormGroup({});
  @Input('controlName') controlName: string = '';
  @Input('page') page: string = '';

  public validations: {type: string, message: string}[] = [];

  constructor() { }

  ngOnInit() {
    if(this.page === 'signUp'){
      this.validations = this.validationSignUpPage[this.controlName];
    }else if(this.page === 'login'){
      this.validations = this.validataionLoginPage[this.controlName];
    }else if(this.page === 'password-reset-send'){
      this.validations = this.validationPasswordResetSendPage[this.controlName];
    }else if(this.page === 'password-reset-verify'){
      this.validations = this.validataionPasswordResetVerifyPage[this.controlName];
    }else if(this.page === 'profile'){
      this.validations = this.validationProfilePage[this.controlName];
    }else if(this.page === 'address'){
      this.validations = this.validationAddressPage[this.controlName];
    }
    
  }

  ngAfterViewInit(): void {


  }

}
