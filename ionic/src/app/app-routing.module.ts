import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AuthModule } from './features/auth/auth.module';
import { UserDashboardModule } from './features/user-dashboard/user-dashboard.module';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'user-dashboard',
    pathMatch: 'full'
  },
  {
    path: 'auth',
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'user-dashboard',
    loadChildren: () => import('./features/user-dashboard/user-dashboard.module').then(m => m.UserDashboardModule)
  },
  {
    path: 'helper-communication',
    loadChildren: () => import('./features/helper-communication/helper-communication.module').then( m => m.HelperCommunicationPageModule)
  },
  {
    path: 'student-communication',
    loadChildren: () => import('./features/student-communication/student-communication.module').then( m => m.StudentCommunicationPageModule)
  },
  {
    path: 'info',
    loadChildren: () => import('./features/info/info.module').then(m => m.InfoModule)
  },
  {
    path: 'news',
    loadChildren: () => import('./features/news/news.module').then(m => m.NewsModule)
  },
  {
    path: 'about-us',
    loadChildren: () => import('./features/about-us/about-us.module').then(m => m.AboutUsModule)
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }),

  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
