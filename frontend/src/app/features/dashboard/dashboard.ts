import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  imports: [MatCardModule, MatButtonModule, MatIconModule],
  template: `
    <div class="dashboard-container">
      <h1>Bienvenue, {{ authService.currentUser()?.firstname || 'Utilisateur' }} !</h1>
      <p>Ceci est votre tableau de bord.</p>

      <mat-card class="dashboard-card">
        <mat-card-header>
          <mat-card-title>Gestion des candidatures</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <p>Vous n'avez pas encore de candidatures.</p>
          <button mat-raised-button color="primary">
            <mat-icon>add</mat-icon>
            Ajouter une candidature
          </button>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .dashboard-container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 2rem 1rem;
    }
    .dashboard-container h1 {
      font-size: 2rem;
      color: #333;
      margin-bottom: 1rem;
    }
    .dashboard-card {
      margin-top: 2rem;
    }
  `],
})
export class Dashboard {
  protected readonly authService = inject(AuthService);
}
