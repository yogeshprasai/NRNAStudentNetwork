import { Injectable } from '@angular/core';
import {finalize, Observable, of} from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";
import { throwError } from "rxjs";
import { catchError, } from "rxjs/operators";
import {LoadingController} from "@ionic/angular";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  public isShowing: boolean = false;
  public loading: any = null;

  constructor(private http: HttpClient, private loadingController: LoadingController) { }

  private formatErrors(err: any) {
    console.error('Error occurred ', err);
    return throwError(err.error);
  }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    this.loaderPresent();
    return this.http.get(path, {params})
     .pipe(catchError(this.formatErrors), finalize(() => this.loaderDismiss()));
  }

  post(path: string, body: Object = {}): Observable<any> {
    this.loaderPresent();
    return this.http
      .post(path, body)
      .pipe(catchError(this.formatErrors), finalize(() => this.loaderDismiss()))
  }

  put(path: string, body: Object = {}){
    this.loaderPresent();
    return this.http
      .put(path, body)
      .pipe(catchError(this.formatErrors), finalize(() => this.loaderDismiss()));
  }

  public async loaderPresent(): Promise<void> {
    if (!this.isShowing) {
      this.isShowing = true;
      this.loading = await this.loadingController.create({
        message: "Please wait ...",
        backdropDismiss: true
      });
      await this.loading.present();
    } else {
      this.isShowing = true;
    }
  }

  public async loaderDismiss(): Promise<void> {
    if (this.loading && this.isShowing) {
      this.isShowing = false;
      await this.loadingController.dismiss();
    }
  }

}

