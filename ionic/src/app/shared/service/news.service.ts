import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {catchError} from "rxjs";
import {ApiService} from "../../core/services/api.service";

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private apiService: ApiService) { }

  public getLatestNews(){
    return this.apiService.get(environment.server_url + "/api/news/latestNews");
  }

  public getTopUniversities(){
      return this.apiService.get(environment.server_url + "/api/topUniversities").pipe();
  }
}


