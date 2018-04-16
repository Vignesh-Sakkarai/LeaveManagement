import { Address } from './../../../model/address';
import { Component, OnInit } from '@angular/core';
import { User } from '../../../model/user';
import { AuthenticationService } from '../../../services/authentication.service';
import { Router } from '@angular/router'

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  user: User;
  address: Address;
  successMessage: String = '';
  errorMessage: String = '';
  buttonIsDisabled: Boolean = true;

  constructor(private authService: AuthenticationService, private router: Router) {
    this.user = new User();
    this.user.address = new Address();
   }

  ngOnInit() {

  }

  OnSignUpSubmit () {
    this.authService.registerUser(this.user).subscribe(data => {
        this.router.navigate(['/login']);
        return true;
    }, error => {
        this.errorMessage = 'Please fill all the fields!!';
    });
  }

}
