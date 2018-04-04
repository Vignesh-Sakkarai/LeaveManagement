import { Address } from './../../model/address';
import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  user: User;
  address: Address;
  emailPattern = '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$';
  successMessage: String = '';
  errorMessage: String = '';
  buttonIsDisabled: Boolean = true;

  constructor(private authService: AuthenticationService) {
    this.user = new User();
    this.user.address = new Address();
   }

  ngOnInit() {

  }

  onkeypress () {
    if (this.user.userName != null && this.user.firstName != null && this.user.lastName != null && this.user.emailAddress != null &&
      this.user.password != null && this.user.address.streetName != null && this.user.address.country != null &&
      this.user.address.city != null && this.user.address.zip != null) {
        this.buttonIsDisabled = false;
    } else {
        this.buttonIsDisabled = true;
    }
  }

  OnSignUpSubmit () {
    this.authService.registerUser(this.user).subscribe(data => {
        this.user = new User();
        this.user.address = new Address();
        this.successMessage = 'User Registered Successfully!!';
        document.getElementById('successMessage').removeAttribute('hidden');
        return true;
    }, error => {
        this.errorMessage = 'Please fill all the fields!!';
        document.getElementById('errorMessage').removeAttribute('hidden');
    });
  }

}
