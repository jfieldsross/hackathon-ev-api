import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from 'src/app/auth/auth.service';
import { AlertService } from 'src/app/services/alert.service';
import { DialogLayoutDisplay } from '@costlydeveloper/ngx-awesome-popup';
import { FcError } from 'src/app/messages/fcerror';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {

  public signinForm: FormGroup;
  private em: string = '';
  private pword: string = '';

  constructor(
    public router: Router,
    public authService: AuthService,
    public alertService: AlertService

  ) {
    this.signinForm = this.initSignInForm();

  }


  initSignInForm() {
    return new FormGroup({
      emailAddress: new FormControl(this.em, Validators.compose([Validators.email, Validators.required])),
      password: new FormControl(this.pword, Validators.compose([Validators.minLength(8), Validators.required]))

    });
  }

  submitSignIn() {
    const e = this.signinForm.get('emailAddress');
    const p = this.signinForm.get('password');
    if (e?.valid && p?.valid) {
      this.authService.signInExistingUser(e.value, p.value);
    } else {
      this.alertService.showToast("Access Denied: Invalid Credentials", DialogLayoutDisplay.WARNING, 1700);

    }
  }
  resetPassword() {
    const emailControl = this.signinForm.controls['emailAddress'];
    if (!emailControl.valid) {
      alert('Please input a valid email address.');
    } else {
      this.authService.performPasswordReset(emailControl.value);
    }
  }
}