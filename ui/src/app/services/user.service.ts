import { Injectable } from '@angular/core';
import { User } from '../shared/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private user: User | undefined;
  public static readonly USER_STORE_KEY = "Current-User-Session-Storage";



  constructor() { }


  setCurrentUser(user: User) {
    if (user.uid) {
      this.user = user;
      sessionStorage.setItem(UserService.USER_STORE_KEY, JSON.stringify(user));
    } else {
      sessionStorage.removeItem(UserService.USER_STORE_KEY);
    }
  }

  getCurrentUser(): string | any {
    return sessionStorage.getItem(UserService.USER_STORE_KEY) || undefined;
  }

  resetCurrentUser() {
    sessionStorage.removeItem(UserService.USER_STORE_KEY);
    this.user = undefined;
  }

}



