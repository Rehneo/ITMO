import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from "rxjs";
import {CookieService} from "ngx-cookie-service";


const LOGIN_API = 'http://127.0.0.1:8080/Lab4/api/auth/login';
const REGISTER_API = 'http://127.0.0.1:8080/Lab4/api/auth/register';
const AUTH_API = 'http://127.0.0.1:8080/Lab4/api/auth';

const headers = new HttpHeaders(
  { 'Content-Type': 'application/json' }
);


@Injectable({
  providedIn: 'root',
})

export class AuthService{
  constructor(private http: HttpClient,
              private cookieService: CookieService) {}


  register(username: string | null | undefined, password: string | null | undefined): Observable<any> {
    return this.http.post(
      REGISTER_API,
      {
        username,
        password,
      },
      {
        headers: headers,
        observe: "response"
      }
    );
  }
  login(username: string | null | undefined, password: string | null | undefined): Observable<any> {
    return this.http.post(
      LOGIN_API,
      {
        username,
        password,
      },
      {
        headers: headers,
        observe: "response"
      }
    );
  }


  isAuthenticated(): boolean{
    let token: string | null | undefined = this.cookieService.get("token");
    return !((token == null) || (token == ""));
  }

  logout(): void {
    this.http.delete(AUTH_API, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json',
        'A-Token': this.cookieService.get("token")})
    }).subscribe({
      error: (err) => {
        console.log(err);
      }
    });
  }
}
