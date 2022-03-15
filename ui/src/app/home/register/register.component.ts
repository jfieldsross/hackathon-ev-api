import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/auth/auth.service';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registrationForm: FormGroup = new FormGroup({});

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private apiService: ApiService
  ) { }
  ngOnInit(): void {
    this.registrationForm = this.fb.group
      ({
        email: ['', Validators.required, Validators.email],
        emailConfirm: ['', Validators.required, Validators.email],
        pass: ['', Validators.required, Validators.minLength(8)],
        passConfirm: ['', Validators.required, Validators.minLength(8)]
      });
  }

  submitRegistration(email: string, emailConfirm: string, pw: string, pwConfirm: string): void {

  }

  get formControls() { return this.registrationForm.controls; }


}
