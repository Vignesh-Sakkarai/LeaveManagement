import {TestBed, ComponentFixture} from '@angular/core/testing';
import {LoginComponent} from './login.component';
import {DebugElement} from '@angular/core';
import {By} from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

describe('LoginComponent', () => {
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
        declarations : [LoginComponent],
        imports: [FormsModule, RouterModule]
      });
      fixture = TestBed.createComponent(LoginComponent);
  });

  it('should contains two text boxes for UserName and Password', () => {
    let userNameElement: DebugElement;
    let passwordElement: DebugElement;
    userNameElement = fixture.debugElement.query(By.css('#userName'));
    expect(userNameElement).toBeTruthy();
    passwordElement = fixture.debugElement.query(By.css('#password'));
    expect(passwordElement).toBeTruthy();
  });

  it('should contains the login buttonId', () => {
    let buttonElement: DebugElement;
    buttonElement = fixture.debugElement.query(By.css('#signInButtonId'));
    expect(buttonElement).toBeTruthy();
  });
});
