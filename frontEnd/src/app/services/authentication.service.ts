import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Observable } from 'rxjs/Observable';
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
    return this.http.post(this.baseUrl + '/web/signup', data, options).map((res: Response) => res.json());
  }

  validateLogin(userName: String, password: String): Observable<any> {
    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions({ headers: headers });
    const data = JSON.stringify({userName: userName, password : password});
    return this.http.post(this.baseUrl + '/group/validateLogin', data, options).map((res: Response) => res.json());
  }

}
