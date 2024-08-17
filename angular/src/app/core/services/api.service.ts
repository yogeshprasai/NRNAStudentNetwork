import { Injectable } from '@angular/core';
import { Observable, of } from "rxjs";
import { HttpHeaders, HttpClient, HttpParams } from "@angular/common/http";
import { throwError } from "rxjs";
import { catchError, retry } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*"
    })
  };
  constructor(private http: HttpClient) { }

  private formatErrors(error: any) {
    console.log(error);
    if(error.status == 404){
      return of([]);
    }else if(error.status = 500){
      //retry one more time
      return throwError(error.error);
    }
    return throwError(error.error);
  }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.get(path, {params}).pipe(
      catchError(this.formatErrors
    ));
  }

  put(path: string, body: Object = {}): Observable<any> {
    return this.http
      .put(path, body, this.httpOptions)
      .pipe(catchError(this.formatErrors));
  }

  post(path: string, body: Object = {}): Observable<any> {
    return this.http
      .post(path, body)
      .pipe(catchError(this.formatErrors));
  }

  // delete(path): Observable<any> {
  //   return this.http.delete(path).pipe(catchError(this.formatErrors));
  // }
}

