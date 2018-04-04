import { AuthenticationService } from './services/index';
import { LoginComponent } from './user/login/login.component';
import { HeaderComponent } from './header/header.component';
import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { UserComponent } from './user/user.component';
import { SignupComponent } from './user/signup/signup.component';
import { RouterTestingModule } from '@angular/router/testing'

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent , LoginComponent, HeaderComponent, UserComponent, SignupComponent
      ],
      imports: [FormsModule, RouterTestingModule],
      providers: [AuthenticationService]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
