import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { FormsModule, NgForm } from '@angular/forms';
import { User } from '../../model/user'
import { AuthenticationService } from '../../services/authentication.service'

import { SignupComponent } from './signup.component';

describe('SignupComponent', () => {
  let component: SignupComponent;
  let fixture : ComponentFixture<SignupComponent>;
  let service : AuthenticationService;
  let spy : any;
  let user : User;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ SignupComponent],
      imports: [FormsModule],
      providers: [AuthenticationService]
    })
    .compileComponents();
    fixture = TestBed.createComponent(SignupComponent);
    service = TestBed.get(AuthenticationService);
    component = fixture.componentInstance;
    this.user = new User();
    fixture.detectChanges();
  });

  afterEach(()=>{
    service = null;
  })

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contains the text box id UserName, Password, email, firstName and LastName', ()=>{
    let userNameElement: DebugElement;
    let passwordElement: DebugElement;
    let emailElement: DebugElement;
    let firstNameElement: DebugElement;
    let lastNameElement: DebugElement;
    let streetNameElement: DebugElement;
    let countryElement: DebugElement;
    let cityElement: DebugElement;
    let zipElement: DebugElement;
    userNameElement = fixture.debugElement.query(By.css('#userName'));
    expect(userNameElement).toBeTruthy();
    passwordElement = fixture.debugElement.query(By.css('#password'));
    expect(passwordElement).toBeTruthy();
    emailElement = fixture.debugElement.query(By.css('#email'));
    expect(emailElement).toBeTruthy();
    firstNameElement = fixture.debugElement.query(By.css('#firstName'));
    expect(firstNameElement).toBeTruthy();
    lastNameElement = fixture.debugElement.query(By.css('#lastName'));
    expect(lastNameElement).toBeTruthy();
    streetNameElement = fixture.debugElement.query(By.css('#streetName'));
    expect(streetNameElement).toBeTruthy();
    countryElement = fixture.debugElement.query(By.css('#country'));
    expect(countryElement).toBeTruthy();
    cityElement = fixture.debugElement.query(By.css('#city'));
    expect(cityElement).toBeTruthy();
    zipElement = fixture.debugElement.query(By.css('#zip'));
    expect(zipElement).toBeTruthy();
  })

  it('should contains the signUp buttonId', ()=>{
    let signUpButtonElement: DebugElement;
    signUpButtonElement = fixture.debugElement.query(By.css('#signUpButton'));
    expect(signUpButtonElement).toBeTruthy();
  })

  it('submit the form making a rest call', ()=>{
    let signUpButtonElement: DebugElement;
    service = TestBed.get(AuthenticationService);
    spy = spyOn(service, 'registerUser').and.callThrough();
    signUpButtonElement = fixture.debugElement.query(By.css('#signUpButton'));
    signUpButtonElement.nativeElement.click();
    expect(service.registerUser).toHaveBeenCalled();
  })

});
