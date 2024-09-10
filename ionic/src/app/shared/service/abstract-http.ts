import {HttpHeaders} from "@angular/common/http";

export abstract class AbstractHttpProvider{
    public static headerOptions = new HttpHeaders().set('Accept', 'application/json').set('Content-type', 'application/json;charset=utf-8');

    public static get options() {
        return {
            headers: AbstractHttpProvider.headerOptions,
            withCredentials: true
        };
    }
}