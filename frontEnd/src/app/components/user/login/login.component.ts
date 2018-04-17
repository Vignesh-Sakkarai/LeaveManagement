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
    //reset Login Status before login
    this.authService.logOut();
  }

  OnLoginSubmit() {
    this.authService.validateLogin(this.user.userName, this.user.password).subscribe(data => {
        if(data === true){
          this.router.navigate(['/profile']);
        }else{
          this.user = new User();
          this.errorMessage = 'UserName or Password is wrong!!';
        }
    }, error => {
      this.user = new User();
      this.errorMessage = 'UserName or Password is wrong!!';
    });
  }

}
