import { User } from './model/user';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/user/login/login.component';
import { AuthenticationService } from './services/index';
import { SignupComponent } from './components/user/signup/signup.component';
import { ProfileComponent} from './components/profile/profile.component'
import { UrlPermission } from './urlPermission/url.permission';

const appRoutes : Routes = [
  { path: 'profile', component: ProfileComponent ,canActivate: [UrlPermission] },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: '**', component: LoginComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule, HttpModule, HttpClientModule, FormsModule, RouterModule.forRoot(appRoutes)
  ],
  providers: [AuthenticationService, UrlPermission],
  bootstrap: [AppComponent]
})
export class AppModule { }
