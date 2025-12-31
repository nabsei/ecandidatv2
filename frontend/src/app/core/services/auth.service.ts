import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginDto, LoginRequest } from '../models/login-dto';
import { RegisterRequest } from '../models/register-dto';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  readonly http = inject(HttpClient);
  readonly apiUrl = 'http://localhost:8080/auth';

  readonly isAuthenticated = signal(false);
  readonly currentUser = signal<User | null>(null);
  readonly token = signal<string | null>(null);

  constructor() {
    const storedToken = localStorage.getItem('token');
    if (storedToken) {
      this.token.set(storedToken);
      this.isAuthenticated.set(true);
      this.loadCurrentUser();
    }
  }

  login(email: string, password: string): Observable<LoginDto> {
    const request: LoginRequest = {
      email: email,
      password: password,
    };

    return this.http.post<LoginDto>(`${this.apiUrl}/login`, request).pipe(
      tap((response) => {
        this.token.set(response.token);
        this.isAuthenticated.set(true);
        localStorage.setItem('token', response.token);
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
    localStorage.removeItem('token');
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

  hasRole(role: string): boolean {
    const user = this.currentUser();
    if (!user) return false;
    return user.authorities.some((auth) => auth.authority === role);
  }

  isAdmin(): boolean {
    return this.hasRole('ROLE_ADMIN');
  }
}
