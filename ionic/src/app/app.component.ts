import { Component, DoCheck, OnInit } from '@angular/core';
import { AuthService } from './shared/service/auth.service';
import { Storage } from '@ionic/storage';
import { Router } from '@angular/router';
import { NavigationMetaData } from './shared/interface/navigation';
import { NrnaLinks, NrnaRoutes } from './shared/service/constant';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent implements OnInit {

  public currentColorMode = 'dark';
  public selectedIndex: number = 0;
  public loggedInUserEmail: string = "";
  public appPages: NavigationMetaData[] = [
    { title: NrnaLinks.Helper, url: NrnaRoutes.Helper, icon: 'help' },
    { title: NrnaLinks.Favorites, url: NrnaRoutes.Favorites, icon: 'heart' },
    { title: NrnaLinks.Login, url: NrnaRoutes.Login, icon: 'mail' }
  ];
  public labels = ['Family', 'Friends', 'Notes', 'Work', 'Travel', 'Reminders'];

  constructor(
    private authService: AuthService,
    private storage: Storage,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.menuBarHideShow(this.authService.isLoggedIn);
    this.authService.login$.subscribe(isLoggedIn => {
      this.menuBarHideShow(isLoggedIn);
    });
  }

  ionViewWillEnter(): void {
    
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
    if(loggedInUser && loggedInUser.email){
      this.loggedInUserEmail = loggedInUser.email;
    }
  }

  public onClickingMenu(navigationData: NavigationMetaData){
    if(this.authService.isLoggedIn && navigationData.title === NrnaLinks.Logout && navigationData.url === NrnaRoutes.Logout){
      this.authService.logout();
      this.appPages.forEach(eachNav => {
        if(eachNav.title === NrnaLinks.Logout && navigationData.url === NrnaRoutes.Logout){
          eachNav.title = NrnaLinks.Login;
          eachNav.url = NrnaRoutes.Login;
        }
      });
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
