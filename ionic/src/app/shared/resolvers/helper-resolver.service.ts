import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable, of } from 'rxjs';
import { HelperService } from '../service/helper.service';

@Injectable({
  providedIn: 'root'
})
export class HelperResolverService implements Resolve<any>{

  constructor(private helperService: HelperService) { 

  }

  resolve(): Observable<any>{
    return this.helperService.getAllHelpers();
  }
}
