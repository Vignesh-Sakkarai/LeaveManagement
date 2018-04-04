import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Observable } from 'rxjs/Observable';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService {

  baseUrl = "http://localhost:8080";

  constructor(private http: Http) { }

  registerUser(user: User): Observable<any>{
    let headers = new Headers({'Content-Type' : 'application/json'});
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(user);
    console.log(":::::::::" + body)
    return this.http.post(this.baseUrl+'/web/signup', body, options).map((res: Response) => res.json());
  }

}
