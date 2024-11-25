import {Component, NgZone, OnInit} from '@angular/core';
import { AuthService } from './shared/service/auth.service';
import { Storage } from '@ionic/storage';
import { Router } from '@angular/router';
import { NavigationMetaData } from './shared/interface/navigation';
import { NrnaLinks, NrnaRoutes } from './shared/service/constant';
import {finalize} from "rxjs";
import {App, URLOpenListenerEvent} from "@capacitor/app";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent implements OnInit {

  public currentColorMode = 'dark';
  public selectedIndex: number = 0;
  public id: number = 0;
  public appPages: NavigationMetaData[] = [
    { title: NrnaLinks.Helper, url: NrnaRoutes.Helper, icon: 'help' },
    { title: NrnaLinks.Student, url: NrnaRoutes.Student, icon: 'help' },
    { title: NrnaLinks.Info, url: NrnaRoutes.Info, icon: 'mail'},
    { title: NrnaLinks.News, url: NrnaRoutes.News, icon: 'mail'},
    { title: NrnaLinks.Login, url: NrnaRoutes.Login, icon: 'mail' }
  ];
  public labels = ['Family', 'Friends', 'Notes', 'Work', 'Travel', 'Reminders'];

  constructor(
    private authService: AuthService,
    private storage: Storage,
    private router: Router,
    private zone: NgZone
  ) {
    this.initializeApp();
  }

  ngOnInit(): void {
    this.menuBarHideShow(this.authService.isLoggedIn);
    this.authService.login$.subscribe(isLoggedIn => {
      this.menuBarHideShow(isLoggedIn);
    });
  }

  ionViewWillEnter(): void {
    
  }

  initializeApp() {
    App.addListener('appUrlOpen', (event: URLOpenListenerEvent) => {
      this.zone.run(() => {
        // Example url: https://beerswift.app/tabs/tab2
        // slug = /tabs/tab2
        const slug = event.url.split(".app").pop();
        if (slug) {
          this.router.navigateByUrl(slug);
        }
        // If no match, do nothing - let regular routing
        // logic take over
        this.router.navigateByUrl("/");
      });
    });
  }

  private menuBarHideShow(isLoggedIn: boolean){
    if(isLoggedIn){
      this.appPages.forEach(eachNav => {
        if(eachNav.title === NrnaLinks.Login && eachNav.url === NrnaRoutes.Login){
          eachNav.title = NrnaLinks.Logout;
          eachNav.url = NrnaRoutes.Logout;
        }
      });
      this.appPages.unshift({ title: NrnaLinks.Profile, url: NrnaRoutes.Profile, icon: 'mail' });
    }else{
      this.appPages.forEach(eachNav => {
        if(eachNav.title === NrnaLinks.Logout && eachNav.url === NrnaRoutes.Logout){
          eachNav.title = NrnaLinks.Login;
          eachNav.url = NrnaRoutes.Login
        }
      });
      const index = this.appPages.findIndex(eachNav => eachNav.title === NrnaLinks.Profile);
      if(index > -1){
        this.appPages.splice(index, 1);
      }
    }
  }

  public update(){
    const loggedInUser = this.authService.loggedInUser;
    if(loggedInUser && loggedInUser.id){
      this.id = loggedInUser.id;
    }
  }

  public onClickingMenu(navigationData: NavigationMetaData){
    if(this.authService.isLoggedIn && navigationData.title === NrnaLinks.Logout && navigationData.url === NrnaRoutes.Logout){
      this.authService.logout().pipe(
          finalize (() => {
            this.authService.removeUserAndToken();
            this.menuBarHideShow(false);
            this.router.navigate(['auth/sign-in']);
            this.appPages.forEach(eachNav => {
              if(eachNav.title === NrnaLinks.Logout && navigationData.url === NrnaRoutes.Logout){
                eachNav.title = NrnaLinks.Login;
                eachNav.url = NrnaRoutes.Login;
              }
            });
          })
      ).subscribe();
    }
    this.router.navigate([navigationData.url]);
  }

  public toggleTheme() {
    if(this.currentColorMode == 'dark'){
      document.body.classList.remove('dark');
      document.body.classList.remove('super-light');
      document.body.classList.add('light');
      this.currentColorMode = 'light';
      this.storage.set('theme', 'light');
    }else if(this.currentColorMode == 'light'){
      document.body.classList.remove('dark');
      document.body.classList.remove('light');
      document.body.classList.add('super-light');
      this.currentColorMode = 'super-light';
      this.storage.set('theme', 'super-light');
    }else if(this.currentColorMode == 'super-light'){
      document.body.classList.remove('super-light');
      document.body.classList.remove('light');
      document.body.classList.add('dark');
      this.currentColorMode = 'dark';
      this.storage.set('theme', 'dark');
    }
  }


}
