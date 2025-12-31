import { Routes } from '@angular/router';
import { authGuard, adminGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./features/home/home').then((m) => m.Home),
  },
  {
    path: 'login',
    loadComponent: () => import('./features/login/login').then((m) => m.Login),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./features/register/register').then((m) => m.Register),
  },
  {
    path: 'formations',
    loadComponent: () =>
      import('./features/formations/formations').then((m) => m.Formations),
  },
  {
    path: 'faq',
    loadComponent: () => import('./features/faq/faq').then((m) => m.Faq),
  },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./features/dashboard/dashboard').then((m) => m.Dashboard),
    canActivate: [authGuard],
  },
  {
    path: 'admin',
    loadComponent: () =>
      import('./features/admin-dashboard/admin-dashboard').then(
        (m) => m.AdminDashboard
      ),
    canActivate: [adminGuard],
  },
  {
    path: '**',
    redirectTo: '',
  },
];
