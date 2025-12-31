import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-home',
  imports: [RouterLink, MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  protected readonly features = [
    {
      icon: 'touch_app',
      title: 'Facilité d\'utilisation',
      description: 'Une interface intuitive et facile à utiliser pour soumettre vos candidatures.',
      image: '/assets/facilite_utilisation.jpg',
    },
    {
      icon: 'school',
      title: 'Accès à toutes les formations',
      description: 'Postulez à une large gamme de formations proposées par l\'université.',
      image: '/assets/acces_formation.jpg',
    },
    {
      icon: 'timeline',
      title: 'Suivi en temps réel',
      description: 'Suivez l\'état de votre candidature en temps réel depuis votre compte.',
      image: '/assets/suivi_temps_reel.jpg',
    },
  ];
}
