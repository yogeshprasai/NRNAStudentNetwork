import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { NrnaLinks, NrnaRoutes } from './constant';
import { NavigationMetaData } from '../interface/navigation';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  public currentAvailablePages: NavigationMetaData[] = [];

  public navigationPages$: Subject<NavigationMetaData[]> = new Subject<NavigationMetaData[]>();

  readonly appPages: NavigationMetaData[] = [
    { title: NrnaLinks.Profile, url: NrnaRoutes.Profile, icon: 'people'},
    { title: NrnaLinks.Helper, url: NrnaRoutes.Helper, icon: 'people' },
    { title: NrnaLinks.Student, url: NrnaRoutes.Student, icon: 'people-circle' },
    { title: NrnaLinks.Info, url: NrnaRoutes.Info, icon: 'information-circle'},
    { title: NrnaLinks.News, url: NrnaRoutes.News, icon: 'newspaper'},
    { title: NrnaLinks.AboutUs, url: NrnaRoutes.AboutUs, icon: 'information-circle'},
    { title: NrnaLinks.Login, url: NrnaRoutes.Login, icon: 'log-in' },
    { title: NrnaLinks.Logout, url: NrnaRoutes.Logout, icon: 'log-out' }
  ];

  constructor(private authService: AuthService) {
    this.reArrangeMenuItem();
   }

  public reArrangeMenuItem(): void {
    const isLoggedIn = this.authService.isLoggedIn;
    if(isLoggedIn){
      this.currentAvailablePages = this.appPages.filter(eachNav => eachNav.title !== NrnaLinks.Login );
    }else{
      this.currentAvailablePages = this.appPages.filter(eachNav => eachNav.title !== NrnaLinks.Profile && eachNav.title !== NrnaLinks.Logout);
    }
    this.navigationPages$.next(this.currentAvailablePages);
  }

public getNavigationMenu(): NavigationMetaData[] {
    return this.currentAvailablePages;
  }
}
