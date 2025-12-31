import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const notificationService = inject(NotificationService);
  const token = authService.getToken();

  if (token) {
    req = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`),
    });
  }

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      // 401 Unauthorized - Session expirée
      if (error.status === 401) {
        authService.logout();
        router.navigate(['/login']);
        notificationService.error('Votre session a expiré. Veuillez vous reconnecter.');
      }
      // 403 Forbidden - Permissions insuffisantes
      else if (error.status === 403) {
        notificationService.error('Vous n\'avez pas les permissions requises pour accéder à cette ressource.');
        router.navigate(['/']);
      }
      // 409 Conflict - Email déjà utilisé (register)
      else if (error.status === 409) {
        notificationService.error('Cette adresse email est déjà utilisée.');
      }
      // 500+ Server errors
      else if (error.status >= 500) {
        notificationService.error('Une erreur serveur s\'est produite. Veuillez réessayer plus tard.');
      }
      // Autres erreurs
      else {
        notificationService.error(
          error.error?.message || 'Une erreur est survenue.'
        );
      }

      return throwError(() => error);
    })
  );
};
