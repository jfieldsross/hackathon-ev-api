import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { DialogLayoutDisplay } from '@costlydeveloper/ngx-awesome-popup';
import firebase from 'firebase/app';
import { AlertService } from "../services/alert.service"
import { ApiService } from "../services/api.service";
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { BasicMessage } from '../messages/message';
import { Observable } from 'rxjs';
import { concatMap, flatMap, mergeMap, switchMap } from 'rxjs/operators';
import { FcError } from '../messages/fcerror';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public signedIn = false;

  constructor(
    private fireAuth: AngularFireAuth,
    private apiService: ApiService,
    private router: Router,
    public alertService: AlertService,
    private userService: UserService
  ) {
    this.signedIn = !!sessionStorage.getItem(UserService.USER_STORE_KEY);
  }


  handleSignIn(firebaseUser: firebase.User | null) {
    if (firebaseUser !== null && firebaseUser !== undefined) {
      firebaseUser.getIdToken().then(idToken => {
        this.apiService.newUserSession(idToken).subscribe((userResult) => {
          if (userResult.uid) {
            this.userService.setCurrentUser(userResult);
            this.signedIn = true;
            this.router.navigate(['/home']);
            this.alertService.showToast("Sign in successful", DialogLayoutDisplay.SUCCESS, 1500);

          }
        })
      })
        .catch((error) => {
          this.alertService.showErrorAlert(new FcError("103", "Authentication Error: check credentials"), "OK", DialogLayoutDisplay.WARNING);
        })
    }
  }

  signInExistingUser(email: string, password: string): void {
    this.fireAuth.setPersistence(firebase.auth.Auth.Persistence.NONE).then(v => {
      this.fireAuth.signInWithEmailAndPassword(email, password).then((userCredentials) => {
        // Get the user's ID token as it is needed to exhange for a session cookie.
        const firebaseUser = userCredentials.user;
        this.handleSignIn(firebaseUser);

      })
        .catch((error) => {
          this.alertService.showErrorAlert(new FcError("102", "Login Attempt Failed, You can reset your password on the login screen."), "OK", DialogLayoutDisplay.WARNING);
        })

    })
  }

  signInWithGoogle() {
    this.fireAuth.setPersistence(firebase.auth.Auth.Persistence.NONE).then(v => {
      this.fireAuth.signInWithPopup(new firebase.auth.GoogleAuthProvider()).then(userCredentials => {
        // Get the user's ID token as it is needed to exhange for a session cookie.
        const firebaseUser = userCredentials.user;
        this.handleSignIn(firebaseUser);

      })
        .catch((error) => {
          this.alertService.showErrorAlert(new FcError("101", "Login Attempt Failed, You can reset your password on the login screen."), "Okay", DialogLayoutDisplay.WARNING);
        })

    })
  }


  registerNewUser(email: any, password: any) {
    this.fireAuth.createUserWithEmailAndPassword(email, password).then(value => {
      this.alertService.showAlert("Account was created successfully. Please sign in using the credentials you just registered.", "Okay", DialogLayoutDisplay.SUCCESS);
      this.router.navigate(['/sign-in']);
    },
      reason => {
        this.alertService.showErrorAlert(reason, "Okay", DialogLayoutDisplay.WARNING);
      }
    );

  }

  verifyUserEmail() {

  }

  isEmailVerified(): boolean {
    return this.userService.getCurrentUser().emailVerified;
  }

  performPasswordReset(email: string) {
    this.fireAuth.sendPasswordResetEmail(email).then(r => {
      alert('Please follow the reset link we just sent to your email address');
    });
  }

  public signOutUser() {
    this.apiService.endUserSession()
      .toPromise().then(result => {
        this.fireAuth.signOut().then(res => {
          this.userService.resetCurrentUser();
          this.signedIn = false;
          this.router.navigate(['/sign-in']);
          this.alertService.showToast("Sign Out Completed: " + result.message, DialogLayoutDisplay.SUCCESS, 1500);
        });
      });
  }

  public isSignedIn() {
    return this.signedIn;
  }


}
