import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'user-dashboard',
    pathMatch: 'full'
  },
  {
    path: 'admin',
    loadChildren: () => import('./features/admin/admin.module').then(m => m.AdminModule)
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
    path: 'volunteer-communication',
    loadChildren: () => import('./features/volunteer-communication/volunteer-communication.module').then(m => m.VolunteerCommunicationModule)
  },
  {
    path: 'student-communication',
    loadChildren: () => import('./features/student-communication/student-communication.module').then( m => m.StudentCommunicationPageModule)
  },
  {
    path: 'university-outreach',
    loadChildren: () => import('./features/university-outreach/university-outreach.module').then(m => m.UniversityOutreachModule)
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
