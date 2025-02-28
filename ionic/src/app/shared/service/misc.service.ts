import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ApiService} from "../../core/services/api.service";
import {environment} from "../../../environments/environment";
import {catchError} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class MiscService {

    constructor(private apiService: ApiService) { }

    public getUniversityOutreachList(){
        return this.apiService.get(environment.server_url + "/api/universityOutreachList").pipe(
            catchError(err => {
                throw new Error("Error while retrieving News. " + err);
            }));
    }
}