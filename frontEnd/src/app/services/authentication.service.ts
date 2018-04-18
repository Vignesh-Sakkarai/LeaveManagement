import { any } from 'codelyzer/util/function';
import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Observable } from 'rxjs/Rx';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService {

  baseUrl = 'http://localhost:8080';

  constructor(private http: Http) { }

  registerUser(user: User): Observable<any> {
    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions({ headers: headers });
    const data = JSON.stringify(user);
    return this.http.post(this.baseUrl + '/web/signup', data, {headers: headers}).map((res: Response) => res.json());
  }

  validateLogin(userName: String, password: String): Observable<boolean> {
    const headers = new Headers({ 'Content-Type': 'application/json'});
    const options = new RequestOptions({ headers: headers });
    const data = {'userName': userName, 'passWord' : password};
    return this.http.post(this.baseUrl + '/group/validateLogin', data, options).map((res: Response) => {
        //Login Successfull check if there is a token in the response
        let token = res.json() && res.json().token;
        if(token){
          localStorage.setItem('currentUser', JSON.stringify({userName: userName, token: token}));
          return true;
        }else{
          return false;
        }
    }).catch((error:any) => Observable.throw(error.json().error || 'Server Error!!'));
  }

  getToken(): String{
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    var token = currentUser && currentUser.token;
    return token ? token : "";
  }

  isLoggedIn():boolean{
    var token: String = this.getToken();
    return token && token.length > 0;
  }

  logOut() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }

  getUserProfile(userName: String): Observable<User>{
    const headers = new Headers({'Content-Type' : 'application/json', 'Authorization' : 'Bearer ' + this.getToken()});
    const options = new RequestOptions({headers: headers});
    return this.http.get(this.baseUrl+"/group/"+userName, options).map((res: Response) => {
          res.json()
    }).catch((error:any) => Observable.throw(error.json().error));
  }

}
