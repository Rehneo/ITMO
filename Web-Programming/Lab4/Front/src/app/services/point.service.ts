import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";
import {CookieService} from "ngx-cookie-service";
import {PointResponse} from "../model/PointResponse";
import {PointRequest} from "../model/PointRequest";


const MAIN_API = 'http://127.0.0.1:8080/Lab4/api/main';




@Injectable({
  providedIn: 'root',
})

export class PointService{

  constructor(private http: HttpClient,
              private cookieService: CookieService) {}






  postPoint(pointRequest: PointRequest): Observable<PointResponse> {
    return this.http.post<PointResponse>(MAIN_API, pointRequest, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json',
        'A-Token': this.cookieService.get("token")})
    });
  }
  getPoints(): Observable<PointResponse[]> {
    return this.http.get<PointResponse[]>(MAIN_API,{
      headers: new HttpHeaders({ 'Content-Type': 'application/json',
        'A-Token': this.cookieService.get("token")})
    });
  }
  deletePoints(): void {
    this.http.delete(MAIN_API, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json',
        'A-Token': this.cookieService.get("token")})
    }).subscribe({
      error: (err) => {
        console.log(err);
      }
    });
  }
}
