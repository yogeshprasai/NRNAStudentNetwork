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

  post(path: string, body: Object = {}): Observable<any> {
    return this.http
      .post(path, body)
      .pipe(catchError(this.formatErrors));
  }
}

