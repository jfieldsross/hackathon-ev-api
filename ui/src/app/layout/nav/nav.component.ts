import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from "@angular/router";
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {

  public activeRouteId: string | undefined;
  readonly aboutPageLink: string = "https://github.com/HarryDulaney/firebase-auth-full-stack-starter";

  constructor(
    public router: Router,
    private activedRoute: ActivatedRoute,
    public authService: AuthService
  ) {
  }
  ngOnInit() {
    this.activedRoute.queryParams.subscribe(params => {
      this.activeRouteId = params['name'];
    });
  }
}
