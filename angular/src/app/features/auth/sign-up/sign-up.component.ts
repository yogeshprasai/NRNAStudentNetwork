import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertController, LoadingController } from '@ionic/angular';
import { Validation_SignUp } from 'src/app/shared/validation';
import { AuthService } from 'src/app/shared/service/auth.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss'],
})
export class SignUpComponent  implements OnInit {

  public isEmailRegistered: boolean = false;
  public isEmailAlreadyExist: boolean = false;
  public validationSignUp = Validation_SignUp;

  public signUpForm: FormGroup = this.formBuilder.group({});

  constructor(private router: Router, private formBuilder: FormBuilder,
    private loadingCtrl: LoadingController, private alertController: AlertController,
    private authService: AuthService) { 
      this.signUpForm = this.formBuilder.group({
        email: ['', Validators.compose([
          Validators.required,
          Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"),
        ])],
        password: ['', Validators.compose([
          Validators.required,
          Validators.minLength(6)
        ])]
      });
    }

  ngOnInit() {
    console.log("hello");
  }

  //Submit Creds also
  submitCredentials(signUpForm: FormGroup){
    const email = signUpForm.get('email')?.value;
    const password = signUpForm.get('password')?.value;
    if(signUpForm.get('email')?.invalid || signUpForm.get('password')?.invalid){
      console.log('Form has erros ', signUpForm);
      return;
    }
    this.authService.signup(email, password).subscribe(data => {
        if(data.message === "Success"){
          this.isEmailRegistered = true;
          //this.router.navigate(['\sign-in']);
        }else if(data.message === "EmailExist"){
          this.isEmailAlreadyExist = true;
        }
    })
  }

  public successButtons = [
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.resetButtons();
        this.router.navigate(['auth/sign-in']);
      },
    },
  ];

  public failureButtons = [
    {
      text: 'OK',
      role: 'confirm',
      handler: () => {
        this.resetButtons();
      },
    },
  ];

  resetButtons(){
    this.isEmailRegistered = false;
    this.isEmailAlreadyExist = false;
  }

}
