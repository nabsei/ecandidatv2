import { Component, inject, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatBadgeModule } from '@angular/material/badge';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { FormationService } from '../../core/services/formation.service';
import { AuthService } from '../../core/services/auth.service';
import { Formation } from '../../core/models/formation';
import { catchError, of, startWith, map } from 'rxjs';

@Component({
  selector: 'app-formations',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatChipsModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatPaginatorModule,
  ],
  templateUrl: './formations.html',
  styleUrl: './formations.css',
})
export class Formations implements OnInit {
  private readonly formationService = inject(FormationService);
  private readonly authService = inject(AuthService);
  private readonly fb = inject(FormBuilder);

  protected readonly loading = signal(true);
  protected readonly allFormations = signal<Formation[]>([]);
  protected readonly selectedUfr = signal<string | null>(null);
  protected readonly selectedLevel = signal<string | null>(null);
  protected readonly pageIndex = signal(0);
  protected readonly pageSize = signal(12);

  protected readonly searchControl = this.fb.nonNullable.control('');

  protected readonly ufrList = [
    { name: 'UFR Santé', icon: 'medical_services', color: '#e91e63' },
    { name: 'UFR Sciences', icon: 'science', color: '#4caf50' },
    { name: 'UFR Lettres et Sciences Humaines', icon: 'menu_book', color: '#ff9800' },
    { name: 'UFR Droit et Sciences Politiques', icon: 'gavel', color: '#f44336' },
    { name: 'UFR Informatique', icon: 'computer', color: '#2196f3' },
    { name: 'UFR Économie et Gestion', icon: 'business', color: '#9e9e9e' },
  ];

  protected readonly levelsList = computed(() => {
    const levels = [...new Set(this.allFormations().map(f => f.level))];

    const levelOrder: { [key: string]: number } = {
      'Licence 1': 1,
      'Licence 2': 2,
      'Licence 3': 3,
      'Master 1': 4,
      'Master 2': 5,
      'Doctorat 1': 6,
      'Doctorat 2': 7,
      'Doctorat 3': 8,
    };

    return levels.sort((a, b) => {
      const orderA = levelOrder[a] ?? 999;
      const orderB = levelOrder[b] ?? 999;
      return orderA - orderB;
    });
  });

  protected readonly searchTerm = signal('');

  protected readonly filteredFormations = computed(() => {
    let formations = this.allFormations();
    const searchValue = this.searchTerm().toLowerCase();
    const ufr = this.selectedUfr();
    const level = this.selectedLevel();

    if (ufr) {
      formations = formations.filter(f => f.ufr === ufr);
    }

    if (level) {
      formations = formations.filter(f => f.level === level);
    }

    if (searchValue) {
      formations = formations.filter(f =>
        f.name.toLowerCase().includes(searchValue) ||
        f.level.toLowerCase().includes(searchValue) ||
        f.ufr.toLowerCase().includes(searchValue)
      );
    }

    return formations;
  });

  protected readonly paginatedFormations = computed(() => {
    const formations = this.filteredFormations();
    const startIndex = this.pageIndex() * this.pageSize();
    const endIndex = startIndex + this.pageSize();
    return formations.slice(startIndex, endIndex);
  });

  protected readonly filteredOptions = this.searchControl.valueChanges.pipe(
    startWith(''),
    map(value => this._filter(value || ''))
  );

  protected getUfrCount(ufrName: string): number {
    const ufr = this.selectedUfr();
    const level = this.selectedLevel();
    let formations = this.allFormations().filter(f => f.ufr === ufrName);

    if (level) {
      formations = formations.filter(f => f.level === level);
    }

    return formations.length;
  }

  protected getLevelCount(levelName: string): number {
    const ufr = this.selectedUfr();
    let formations = this.allFormations().filter(f => f.level === levelName);

    if (ufr) {
      formations = formations.filter(f => f.ufr === ufr);
    }

    return formations.length;
  }

  ngOnInit(): void {
    this.loadFormations();

    this.searchControl.valueChanges.subscribe(value => {
      this.searchTerm.set(value || '');
      this.pageIndex.set(0);
    });
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
        this.allFormations.set(formations);
        this.loading.set(false);
      });
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    const suggestions = new Set<string>();

    this.allFormations().forEach(formation => {
      if (formation.name.toLowerCase().includes(filterValue)) {
        suggestions.add(formation.name);
      }
    });

    return Array.from(suggestions).slice(0, 5);
  }

  selectUfr(ufrName: string): void {
    if (this.selectedUfr() === ufrName) {
      this.selectedUfr.set(null);
    } else {
      this.selectedUfr.set(ufrName);
    }
    this.pageIndex.set(0);
  }

  selectLevel(level: string): void {
    if (this.selectedLevel() === level) {
      this.selectedLevel.set(null);
    } else {
      this.selectedLevel.set(level);
    }
    this.pageIndex.set(0);
  }

  clearFilters(): void {
    this.searchControl.setValue('');
    this.searchTerm.set('');
    this.selectedUfr.set(null);
    this.selectedLevel.set(null);
    this.pageIndex.set(0);
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
  }

  getUfrIcon(ufrName: string): string {
    return this.ufrList.find(u => u.name === ufrName)?.icon || 'school';
  }

  getUfrColor(ufrName: string): string {
    return this.ufrList.find(u => u.name === ufrName)?.color || '#757575';
  }

  onOptionSelected(event: MatAutocompleteSelectedEvent): void {
    this.searchTerm.set(event.option.value);
  }

  hasActiveFilters(): boolean {
    return !!(this.selectedUfr() || this.selectedLevel() || this.searchTerm());
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  onCandidater(formation: Formation): void {
  }
}
