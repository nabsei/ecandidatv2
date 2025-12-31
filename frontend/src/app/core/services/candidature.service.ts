import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Candidature } from '../models/candidature';

@Injectable({
  providedIn: 'root',
})
export class CandidatureService {
  readonly http = inject(HttpClient);
  readonly apiUrl = 'http://localhost:8080/api/candidatures';

  loadCandidatures(): Observable<Candidature[]> {
    return this.http.get<Candidature[]>(this.apiUrl);
  }

  loadCandidatureById(id: string): Observable<Candidature> {
    return this.http.get<Candidature>(`${this.apiUrl}/${id}`);
  }

  createCandidature(candidature: FormData): Observable<Candidature> {
    return this.http.post<Candidature>(this.apiUrl, candidature);
  }

  updateCandidature(id: string, candidature: Candidature): Observable<Candidature> {
    return this.http.put<Candidature>(`${this.apiUrl}/${id}`, candidature);
  }

  deleteCandidature(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  loadAllCandidatures(): Observable<Candidature[]> {
    return this.http.get<Candidature[]>(`${this.apiUrl}/admin/all`);
  }

  updateCandidatureStatus(id: string, status: string): Observable<Candidature> {
    return this.http.patch<Candidature>(`${this.apiUrl}/admin/${id}/status`, { status });
  }
}
