import { AuthenticationService } from './../../services/authentication.service';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from "../../model/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit {
  currentUser: User;
  user: User;
  errorMessage: String;
  constructor(public authService: AuthenticationService, public router: Router) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.authService.getUserProfile("Vignesh").subscribe(user =>{
      this.user = user;
    }, error => {
      this.router.navigate(['/login']);
      this.errorMessage = 'Invalid Login, Please login again with valid credentials!!';
    })
  }

  ngOnInit() { }

// login out from the app
  logOut() {
    this.authService.logOut();
  }
}
