import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-admin-dashboard',
  imports: [MatCardModule, MatIconModule],
  template: `
    <div class="admin-container">
      <h1>Tableau de bord administrateur</h1>
      <p>Gestion des candidatures pour toutes les formations.</p>

      <mat-card class="admin-card">
        <mat-card-header>
          <mat-card-title>
            <mat-icon>admin_panel_settings</mat-icon>
            Liste des candidatures
          </mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <p>Aucune candidature pour le moment.</p>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .admin-container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 2rem 1rem;
    }
    .admin-container h1 {
      font-size: 2rem;
      color: #c10330;
      margin-bottom: 1rem;
    }
    .admin-card {
      margin-top: 2rem;
    }
  `],
})
export class AdminDashboard {}
