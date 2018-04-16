import { AuthenticationService } from './../../../services/authentication.service';
import { User } from './../../../model/user';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  user: User;
  errorMessage: String;
  buttonIsDisabled: Boolean;
  constructor(private authService: AuthenticationService, private router:Router) {
    this.user = new User();
  }

  ngOnInit() {

  }

  OnLoginSubmit() {
    this.authService.validateLogin(this.user.userName, this.user.password).subscribe(data => {
        localStorage.setItem('currentUser', JSON.stringify(this.user));
        this.router.navigate(['/profile']);
        return true;
    }, error => {
      this.user = new User();
      this.errorMessage = 'UserName or Password is wrong!!';
    });
  }

}
