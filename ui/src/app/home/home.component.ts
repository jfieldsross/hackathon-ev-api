import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { BasicMessage } from '../messages/message';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  content: string = '';
  private homeMessage = 'greeting';

  constructor(
    router: Router,
    private apiService: ApiService
  ) {

  }
  ngOnInit(): void {
    this.apiService.getMessage(this.homeMessage).subscribe((data: BasicMessage) => this.content = data.content);
  }


}
