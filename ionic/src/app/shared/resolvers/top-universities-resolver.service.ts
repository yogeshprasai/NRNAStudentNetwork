import {NewsService} from "../service/news.service";
import {Observable} from "rxjs";
import {Resolve} from "@angular/router";
import {Injectable} from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class TopUniversitiesResolverService implements Resolve<any>{
    constructor(public newsService: NewsService) {
    }
    resolve(): Observable<any> {
        return this.newsService.getTopUniversities();
    }

}