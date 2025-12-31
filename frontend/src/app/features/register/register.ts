import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AuthService } from '../../core/services/auth.service';
import { NotificationService } from '../../core/services/notification.service';
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-register',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);
  private readonly notificationService = inject(NotificationService);

  protected readonly hidePassword = signal(true);
  protected readonly hideConfirmPassword = signal(true);
  protected readonly loading = signal(false);
  protected readonly errorMessage = signal<string | null>(null);

  protected readonly registerForm = this.fb.nonNullable.group({
    firstname: ['', [Validators.required, Validators.minLength(2)]],
    lastname: ['', [Validators.required, Validators.minLength(2)]],
    email: ['', [Validators.required, Validators.email]],
    confirmEmail: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8),
      Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=\-!*()])/)]],
    confirmPassword: ['', [Validators.required]],
  });

  onSubmit(): void {
    if (this.registerForm.invalid) {
      return;
    }

    const formValue = this.registerForm.getRawValue();

    if (formValue.email !== formValue.confirmEmail) {
      this.errorMessage.set('Les adresses email ne correspondent pas.');
      return;
    }

    if (formValue.password !== formValue.confirmPassword) {
      this.errorMessage.set('Les mots de passe ne correspondent pas.');
      return;
    }

    this.loading.set(true);
    this.errorMessage.set(null);

    const registerData = {
      email: formValue.email,
      firstname: formValue.firstname,
      lastname: formValue.lastname,
      password: formValue.password,
    };

    this.authService
      .register(registerData)
      .pipe(
        catchError((error) => {
          if (error.status === 409) {
            this.errorMessage.set('Cette adresse email est déjà utilisée.');
          } else {
            this.errorMessage.set(
              'Une erreur est survenue lors de l\'inscription.'
            );
          }
          this.loading.set(false);
          return of(null);
        })
      )
      .subscribe((response) => {
        if (response) {
          this.loading.set(false);
          this.notificationService.success(
            'Inscription réussie ! Vous pouvez maintenant vous connecter.'
          );
          this.router.navigate(['/login']);
        }
      });
  }

  togglePasswordVisibility(): void {
    this.hidePassword.set(!this.hidePassword());
  }

  toggleConfirmPasswordVisibility(): void {
    this.hideConfirmPassword.set(!this.hideConfirmPassword());
  }
}
