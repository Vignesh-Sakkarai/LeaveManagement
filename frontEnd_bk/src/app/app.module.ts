import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule, Routes } from '@angular/router',

import { AppComponent } from './app.component';
import { LoginComponent } from './user/login/login.component';
import { AuthenticationService } from './services/index';
import { HeaderComponent } from './header/header.component';
import { UserComponent } from './user/user.component';
import { SignupComponent } from './user/signup/signup.component';
import { HomeComponent } from './home/home.component';

const appRoutes : Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    UserComponent,
    SignupComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule, HttpModule, FormsModule, RouterModule.forRoot( appRoutes, { enableTracing: true })
  ],
  providers: [AuthenticationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
