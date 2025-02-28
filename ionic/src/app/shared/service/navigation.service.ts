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
    { title: NrnaLinks.Admin, url: NrnaRoutes.Admin, icon: 'color-wand'},
    { title: NrnaLinks.Profile, url: NrnaRoutes.Profile, icon: 'people'},
    { title: NrnaLinks.Volunteer, url: NrnaRoutes.Volunteer, icon: 'people' },
    { title: NrnaLinks.Student, url: NrnaRoutes.Student, icon: 'people-circle' },
    { title: NrnaLinks.UniversityOutreach, url: NrnaRoutes.UniversityOutreach, icon: 'school' },
    { title: NrnaLinks.Events, url: NrnaRoutes.Events, icon: 'flash' },
    { title: NrnaLinks.Info, url: NrnaRoutes.Info, icon: 'information-circle'},
    { title: NrnaLinks.News, url: NrnaRoutes.News, icon: 'newspaper'},
    { title: NrnaLinks.AboutUs, url: NrnaRoutes.AboutUs, icon: 'information-circle'},
    { title: NrnaLinks.Login, url: NrnaRoutes.Login, icon: 'log-in' },
    { title: NrnaLinks.Logout, url: NrnaRoutes.Logout, icon: 'log-out' }
  ];

  constructor(private authService: AuthService) {
    this.reArrangeMenuItem();
   }

  public reArrangeMenuItem(isAdmin?: boolean): void {
    const isLoggedIn = this.authService.isLoggedIn;
    if(isLoggedIn){
      this.currentAvailablePages = this.appPages.filter(eachNav => eachNav.title !== NrnaLinks.Login );
      this.currentAvailablePages = this.currentAvailablePages.filter(eachNav => {
        if(eachNav.title === NrnaLinks.Admin){
          return isAdmin;
        }
        return true;
      });
    }else{
      this.currentAvailablePages = this.appPages.filter(eachNav => eachNav.title !== NrnaLinks.Admin && eachNav.title !== NrnaLinks.Profile && eachNav.title !== NrnaLinks.Logout);
    }
    this.navigationPages$.next(this.currentAvailablePages);
  }

public getNavigationMenu(): NavigationMetaData[] {
    return this.currentAvailablePages;
  }
}
