import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Formation } from '../models/formation';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FormationService {
  readonly http = inject(HttpClient);
  readonly apiUrl = `${environment.apiUrl}/api/formations`;

  loadFormations(): Observable<Formation[]> {
    return this.http.get<Formation[]>(this.apiUrl);
  }

  loadFormationById(id: string): Observable<Formation> {
    return this.http.get<Formation>(`${this.apiUrl}/${id}`);
  }
}
