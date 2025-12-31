import { Component } from '@angular/core';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-faq',
  imports: [MatExpansionModule, MatCardModule, MatButtonModule],
  templateUrl: './faq.html',
  styleUrl: './faq.css',
})
export class Faq {
  protected readonly faqs = [
    {
      question: 'Je veux candidater/demander une admission, comment je procède ?',
      answer: 'Si vous êtes étudiant à l\'Université de Montpellier : connectez-vous avec vos identifiants ENT, puis suivez la procédure de création de compte...',
    },
    {
      question: 'Je ne trouve pas ma formation, comment je procède ?',
      answer: 'Attention ! Il existe deux plateformes eCandidat au sein de l\'Université de Montpellier. Si vous ne trouvez pas votre formation ici, rendez-vous sur la seconde plateforme : candidature.umontpellier.fr',
    },
    {
      question: 'Qu\'est-ce que le n° INE ? Où trouver mon n° INE ?',
      answer: 'Si vous avez passé le baccalauréat français depuis 1995, vous possédez un INE. Vous le trouverez sur le relevé de notes du baccalauréat ou sur vos relevés de notes de l\'enseignement supérieur.',
    },
    {
      question: 'J\'ai oublié mon mot de passe, que faire ?',
      answer: 'Cliquez sur "Mot de passe oublié ?" depuis la page de connexion. Vous recevrez un email avec un lien pour réinitialiser votre mot de passe.',
    },
  ];
}
