import { Injectable } from '@angular/core';
import { Observable, of } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";
import { throwError } from "rxjs";
import { catchError, } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  private formatErrors(err: any) {
    console.error('Error occurred ', err);
    return throwError(err.error);
  }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.get(path, {params})
     .pipe(catchError(this.formatErrors));
  }

  post(path: string, body: Object = {}): Observable<any> {
    return this.http
      .post(path, body)
      .pipe(catchError(this.formatErrors));
  }

  put(path: string, body: Object = {}){
    return this.http
      .put(path, body)
      .pipe(catchError(this.formatErrors));
  }
}

