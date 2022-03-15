import { Component, OnDestroy, OnInit, } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { BasicMessage } from './messages/message';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = 'File Commander Web';

}


