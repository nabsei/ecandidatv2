import { HttpInterceptorFn, HttpStatusCode } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { NotificationService } from '../services/notification.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const notificationService = inject(NotificationService);

  if (authService.hasToken()) {
    req = req.clone({
      setHeaders: {
        Authorization: 'Bearer ' + authService.getToken(),
      },
    });
  }

  // gestion des erreurs
  return next(req).pipe(
    catchError((error) => {
      if (error.status === HttpStatusCode.Unauthorized) {
        authService.logout();
        notificationService.error("Session d'authentification expirée");
        router.navigate(["/"]);
      } else if (error.status === HttpStatusCode.Forbidden) {
        notificationService.error("Vous n'avez pas le droit d'accéder à cette page");
        router.navigate(["/"]);
      } else if (error.status === HttpStatusCode.Conflict) {
        notificationService.error('Cette adresse email est déjà utilisée.');
      } else if (error.status >= 500) {
        notificationService.error('Oups ! Une erreur serveur est survenue, merci de réessayer plus tard.');
      } else {
        notificationService.error(
          error.error?.message || 'Une erreur est survenue.'
        );
      }

      return throwError(() => error);
    })
  );
};
