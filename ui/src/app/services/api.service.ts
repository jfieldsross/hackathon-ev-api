import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpRequest, HttpResponse } from '@angular/common/http';
import { User } from "../shared/models/user";
import { BasicMessage } from '../messages/message';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { MessagePacket } from '../messages/message-packet';

@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient) {
  }

  /* --------------------------- Authentication and User account API Calls ---------------------------------- */
  newUserSession(idToken: string): Observable<User> {
    const options = {
      headers: new HttpHeaders({ 'Authorization': `Bearer ${idToken}` }),
    };
    return this.http.get<User>(`${environment.apiUrl}/api/user/session-login`, options);
  }


  endUserSession(): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/user/session-logout`, '');
  }

  /* ----------------------------------- Example Messaging API Calls ------------------------------------ */
  getMessage(message: string) {
    return this.http.get<BasicMessage>(`${environment.apiUrl}/api/public/${message}`);
  }

  getUserMessage(message: string) {
    return this.http.get<MessagePacket>(`${environment.apiUrl}/api/user/message/${message}`);
  }

  secureMessage() {
    return this.http.get<BasicMessage>(`${environment.apiUrl}/api/user/secure-message`);
  }

}
