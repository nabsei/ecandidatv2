import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FormationService } from '../../core/services/formation.service';
import { Formation } from '../../core/models/formation';
import { catchError, of } from 'rxjs';

interface FormationsByUfr {
  [ufr: string]: Formation[];
}

@Component({
  selector: 'app-formations',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatExpansionModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './formations.html',
  styleUrl: './formations.css',
})
export class Formations implements OnInit {
  private readonly formationService = inject(FormationService);
  private readonly fb = inject(FormBuilder);

  protected readonly loading = signal(true);
  protected readonly formationsByUfr = signal<FormationsByUfr>({});
  protected readonly filteredFormationsByUfr = signal<FormationsByUfr>({});
  protected readonly ufrColors: Record<string, string> = {
    'UFR Santé': 'primary-color',
    'UFR Sciences': 'success-color',
    'UFR Lettres et Sciences Humaines': 'warning-color',
    'UFR Droit et Sciences Politiques': 'danger-color',
    'UFR Informatique': 'info-color',
    'UFR Économie et Gestion': 'secondary-color',
  };

  protected readonly searchControl = this.fb.nonNullable.control('');

  ngOnInit(): void {
    this.loadFormations();
    this.searchControl.valueChanges.subscribe(() => this.filterFormations());
  }

  private loadFormations(): void {
    this.formationService
      .loadFormations()
      .pipe(
        catchError(() => {
          this.loading.set(false);
          return of([]);
        })
      )
      .subscribe((formations) => {
        const grouped = this.groupFormationsByUfr(formations);
        this.formationsByUfr.set(grouped);
        this.filteredFormationsByUfr.set(grouped);
        this.loading.set(false);
      });
  }

  private groupFormationsByUfr(formations: Formation[]): FormationsByUfr {
    return formations.reduce((acc, formation) => {
      if (!acc[formation.ufr]) {
        acc[formation.ufr] = [];
      }
      acc[formation.ufr].push(formation);
      return acc;
    }, {} as FormationsByUfr);
  }

  private filterFormations(): void {
    const searchTerm = this.searchControl.value.toLowerCase();
    if (!searchTerm) {
      this.filteredFormationsByUfr.set(this.formationsByUfr());
      return;
    }

    const filtered: FormationsByUfr = {};
    Object.entries(this.formationsByUfr()).forEach(([ufr, formations]) => {
      const filteredFormations = formations.filter(
        (f) =>
          f.name.toLowerCase().includes(searchTerm) ||
          f.level.toLowerCase().includes(searchTerm) ||
          ufr.toLowerCase().includes(searchTerm)
      );
      if (filteredFormations.length > 0) {
        filtered[ufr] = filteredFormations;
      }
    });
    this.filteredFormationsByUfr.set(filtered);
  }

  getUfrKeys(): string[] {
    return Object.keys(this.filteredFormationsByUfr());
  }
}
