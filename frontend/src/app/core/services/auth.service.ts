import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginDto, LoginRequest } from '../models/login-dto';
import { RegisterRequest } from '../models/register-dto';
import { User } from '../models/user';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  http = inject(HttpClient);
  readonly apiUrl = `${environment.apiUrl}/auth`;

  readonly isAuthenticated = signal(false);
  readonly currentUser = signal<User | null>(null);
  readonly token = signal<string | null>(null);

  constructor() {
    const storedToken = localStorage.getItem('auth_jwt');
    if (storedToken) {
      this.token.set(storedToken);
      this.isAuthenticated.set(true);
      this.loadCurrentUser();
    }
  }

  signIn(email: string, password: string): Observable<LoginDto> {
    localStorage.removeItem('auth_jwt');
    const request: LoginRequest = {
      email: email,
      password: password,
    };

    return this.http.post<LoginDto>(`${this.apiUrl}/login`, request).pipe(
      tap((response) => {
        this.token.set(response.token);
        this.isAuthenticated.set(true);
        localStorage.setItem('auth_jwt', response.token);
        this.loadCurrentUser();
      })
    );
  }

  register(data: RegisterRequest): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/register`, data);
  }

  logout(): void {
    this.token.set(null);
    this.isAuthenticated.set(false);
    this.currentUser.set(null);
    localStorage.removeItem('auth_jwt');
  }

  private loadCurrentUser(): void {
    const tokenValue = this.token();
    if (tokenValue) {
      try {
        const payload = JSON.parse(atob(tokenValue.split('.')[1]));
        const scope = payload.scope || '';
        const roles = scope.split(' ').filter((r: string) => r.startsWith('ROLE_'));
        const authorities = roles.map((role: string) => ({ authority: role }));

        const user = new User(
          payload.sub || '',
          payload.sub || '',
          '',
          '',
          authorities,
          true,
          true,
          true,
          true
        );
        this.currentUser.set(user);
      } catch (error) {
        console.error('Erreur lors du décodage du token', error);
      }
    }
  }

  getToken(): string | null {
    return this.token();
  }

  hasToken(): boolean {
    const token = localStorage.getItem('auth_jwt');
    return token !== null && token !== '';
  }

  getRoles(): string[] {
    const token = localStorage.getItem('auth_jwt');
    if (token !== null) {
      const tokenParts = token.split('.');
      const payloadJson = atob(tokenParts[1]);
      const payload: any = JSON.parse(payloadJson);
      return payload.scope.split(' ');
    }
    return [];
  }

  hasRole(role: string): boolean {
    const user = this.currentUser();
    if (!user) return false;
    return user.authorities.some((auth) => auth.authority === role);
  }

  isAdmin(): boolean {
    return this.hasRole('ROLE_ADMIN');
  }
}
