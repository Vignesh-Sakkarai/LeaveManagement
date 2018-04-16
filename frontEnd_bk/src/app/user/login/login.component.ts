import { AuthenticationService } from './../../services/authentication.service';
import { User } from './../../model/user';
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

  onkeypress() {
    if (this.user.userName != null && this.user.password != null) {
        this.buttonIsDisabled = false;
    } else {
        this.buttonIsDisabled = true;
    }
  }

  OnLoginSubmit() {
    this.authService.validateLogin(this.user.userName, this.user.password).subscribe(data => {
      this.user = new User();
       document.getElementById('errorMessage').setAttribute('hidden', 'true');
        this.router.navigate
        return true;
    }, error => {
      this.user = new User();
      this.errorMessage = 'UserName or Password is wrong!!';
      document.getElementById('errorMessage').removeAttribute('hidden');
    });
  }

}
