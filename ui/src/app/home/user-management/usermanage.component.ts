import { Component, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { Router, ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AuthService } from 'src/app/auth/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-usermanage',
  templateUrl: './usermanage.component.html',
  styleUrls: ['./usermanage.component.scss']
})
export class UsermanageComponent implements OnInit {
  actionCodeChecked: boolean = false;

  ngUnsubscribe: Subject<any> = new Subject<any>();
  // actions = UserManagementActions;

  mode: string = '';
  actionCode: string = '';
  oldPassword: string = '';
  newPassword: string = '';
  confirmPassword: string = '';

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private userService: UserService,
    private auth: AngularFireAuth
  ) {
  }

  ngOnInit() {
    this.activatedRoute.queryParams.pipe(takeUntil(this.ngUnsubscribe)).subscribe((params) => {
      if (!params) this.router.navigate(['/home']);

      this.mode = params['mode'];
      this.actionCode = params['oobCode'];

      switch (params['mode']) {
        case 'resetPassword': {
          this.auth.verifyPasswordResetCode(this.actionCode).then(email => {
            this.actionCodeChecked = true;
          }).catch(e => {
            // alert(e);/
            this.router.navigate(['/sign-in']);
          });
        } break;
        case 'recoverEmail': {
          //Complete recoverEmail action logic
        } break;
        case 'verifyEmail': {
          //Complete verifyEmail action logic
        } break;
        default: {
          console.log('query parameters must be missing');
          this.router.navigate(['/sign-in']);
        }
      }
    });
  }

  ngOnDestroy() {
    // End all subscriptions listening to ngUnsubscribe
    // to avoid memory leaks.
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}