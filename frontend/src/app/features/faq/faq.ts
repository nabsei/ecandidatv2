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
      answer: 'Vous êtes étudiant à l\'Université de Montpellier : connectez-vous en utilisant vos identifiants ENT (Adresse institutionelle/password) puis suivez la procédure de création de compte. Une fois vos données enregistrées, vous recevrez un mail de confirmation de création de compte contenant un lien pour valider votre compte.\n\nVous n\'êtes pas étudiant à l\'Université de Montpellier : vous devez au préalable créer un compte en cliquant sur le lien Créer un compte dans le bandeau en haut de la page d\'accueil. Une fois vos données enregistrées, vous recevrez un mail de confirmation de création de compte contenant un lien pour valider votre compte.',
    },
    {
      question: 'Je ne trouve pas ma formation, comment je procède ?',
      answer: 'Attention ! Il existe deux plateformes eCandidat au sein de l\'Université de Montpellier.\n\n1. RDV sur la seconde plateforme : https://candidature.umontpellier.fr\n2. Vérifiez que vous trouvez votre formation dans l\'onglet "formation" (menu de gauche)\n3. Une fois votre formation trouvée, créez votre compte et constituez votre dossier\n\nAprès vérification sur les deux plateformes eCandidat, si vous ne trouvez pas votre formation, merci de consulter le site de la composante (faculté, institut ou IUT) concernée : https://www.umontpellier.fr/formation/admission-et-inscription',
    },
    {
      question: 'Comment fusionner plusieurs fichiers en un seul ?',
      answer: 'Si vous devez transmettre plusieurs relevés de notes par exemple sur un seul fichier, vous devez assembler les différents relevés pour constituer un seul fichier. Des outils gratuits existent pour fusionner plusieurs fichiers, certains sites proposent ce service en ligne gratuitement.',
    },
    {
      question: 'J\'ai perdu mon code d\'activation',
      answer: 'Cliquez sur le lien "J\'ai perdu mon code d\'activation" au bas de la page d\'accueil. Il vous suffit de saisir l\'adresse email que vous aviez renseignée, votre code d\'activation vous sera envoyé par mail.',
    },
    {
      question: 'J\'ai oublié mes identifiants',
      answer: 'Cliquez sur le lien "J\'ai oublié mes identifiants" au bas de la page d\'accueil. Il vous suffit de saisir l\'adresse email que vous aviez renseignée, votre numéro de dossier vous sera envoyé par mail.',
    },
    {
      question: 'Je ne reçois pas de mail de l\'application eCandidat',
      answer: 'Certains clients de messagerie peuvent considérer les mails provenant de l\'application eCandidat comme Spams.\n\nSi vous n\'avez pas reçu de mail, vérifiez le contenu de votre dossier "Spam/courriers indésirables".\n\nSi vous n\'avez reçu aucun mail dans votre dossier "Spam/courriers indésirables" dans l\'heure qui suit la création de votre compte, veuillez créer un nouveau compte avec une adresse mail d\'un autre client messagerie.\n\nVeillez à consulter régulièrement cette adresse mail, car vous y recevrez de nombreux mails relatifs à votre candidature sur cette application.',
    },
    {
      question: 'Le lien d\'activation n\'est pas actif dans le mail',
      answer: 'Il peut arriver que le lien d\'activation ne soit pas actif dans le mail que vous recevez. Vous pouvez copier l\'adresse complète du lien et la coller dans la barre d\'adresse de votre navigateur. La page d\'accueil eCandidat s\'affiche avec la mention "Le compte est validé". À ce moment-là connectez-vous avec les identifiants reçus.',
    },
    {
      question: 'Puis-je candidater à plusieurs formations avec un seul compte ?',
      answer: 'Vous n\'avez besoin que d\'un seul compte eCandidat, vous pouvez candidater/demander une admission à plusieurs formations avec votre compte. Pour cela, vous ajoutez une nouvelle candidature dans l\'onglet "Candidatures/demandes d\'admission".',
    },
    {
      question: 'J\'ai déjà créé un compte l\'année passée, puis-je l\'utiliser ?',
      answer: 'Vous devez créer un nouveau compte. Le compte créé l\'année passée n\'est plus actif. Pour cela, veuillez-vous référer à la question : Je veux candidater/demander une admission.',
    },
    {
      question: 'Qu\'est-ce que le n° INE ? Où trouver mon n° INE ?',
      answer: 'Si vous avez passé le baccalauréat français depuis 1995, ou que vous avez déjà été inscrit dans un établissement de l\'enseignement supérieur français depuis cette date, vous possédez un INE ou (numéro BEA). Vous le trouverez :\n\n• Sur le relevé de notes du baccalauréat\n• Sur vos relevés de notes délivrés par un établissement de l\'enseignement supérieur\n\nL\'INE est constitué de 10 caractères alphanumériques et d\'un caractère supplémentaire appelé clé. Attention de bien saisir la clé dans l\'emplacement spécifique. Vérifiez la saisie des "O" et les "0" (zéro) car il peut y avoir confusion, saisissez votre numéro en MAJUSCULES.\n\nSi vous êtes candidat/étudiant étranger n\'ayant jamais été inscrit dans un établissement français vous n\'avez pas de n° INE, vous devez sélectionner la série de bac suivante: "0031-titre étranger admis en équivalence".',
    },
    {
      question: 'L\'application ne reconnaît pas mon INE',
      answer: 'Si vous n\'avez jamais été étudiant(e) à l\'Université de Montpellier, il est normal que l\'application ne reconnaisse pas votre INE. Cliquez sur « Oui » pour continuer la complétude de votre dossier.\n\nSi vous avez déjà été étudiant(e) à l\'Université de Montpellier, vérifiez que vous avez bien saisi votre INE (vérifiez en particulier les 0 et les O et veillez à saisir la clé INE dans son emplacement).',
    },
    {
      question: 'Je ne trouve pas mon établissement dans la liste',
      answer: 'La liste des établissements est affichée à partir du code postal et de la ville que vous avez saisie.\n\n• L\'établissement que vous recherchez se trouve peut-être sur une autre commune que celle que vous avez saisie. Pour le savoir, cherchez l\'adresse postale de l\'établissement puis saisissez bien cette information dans l\'application.\n\n• Si vous avez effectué votre formation dans une composante (faculté, instituts, iut, …) d\'une université, veuillez sélectionner l\'université et apporter des précisions concernant votre cursus au niveau du champ « Libellé et niveau de la formation ».\n\n• Si l\'établissement n\'existe pas dans la liste, contactez l\'adresse mail de contact affichée dans le bloc "Adresse de contact" de la page "Récapitulatif de votre candidature/demande d\'admission".\n\nVous pouvez continuer la création de votre dossier et effectuer des candidatures/demandes d\'admission en attendant la création de l\'établissement.',
    },
    {
      question: 'Les informations préremplies sur mon dossier sont erronées',
      answer: 'Les informations préremplies sont importées depuis Apogée, application dans laquelle est enregistré tout votre cursus au sein de l\'université (informations personnelles et résultats). Si vous constatez une erreur sur votre dossier, contactez le service de scolarité de la composante, concerné par la modification à apporter.',
    },
    {
      question: 'Une pièce justificative a été refusée, que faire ?',
      answer: 'Supprimez la pièce justificative refusée en cliquant sur le signe "-". Téléchargez la nouvelle pièce justificative sur l\'écran récapitulatif de votre candidature à la formation concernée.',
    },
    {
      question: 'Quelle est la procédure pour candidater ?',
      answer: '1. Création d\'un compte eCandidat, saisie du nom, prénom et adresse mail\n2. Envoi d\'un mail contenant le lien de validation du compte et les identifiants de connexion\n3. Saisie d\'informations complémentaires : n°INE, état-civil, adresse, baccalauréat, cursus\n4. Sélection des formations souhaitées\n5. Dépôt des pièces justificatives (format : PDF ou JPG ; taille : 8 Mo maximum par pièce)\n6. Transmission des candidatures en cliquant sur "transmettre ma candidature"\n7. Suivi des candidatures tout au long du processus\n\nCommunication de l\'état du dossier par mails de notification. Veillez à consulter régulièrement votre adresse mail.',
    },
    {
      question: 'Mon dossier est bloqué (message : "Dossier en cours de consultation")',
      answer: 'Votre dossier est actuellement consulté par la commission ou les agents administratifs de l\'université de Montpellier. Vous devez vous reconnecter ultérieurement pour accéder à votre dossier.\n\nAttention ! Le blocage peut venir du fait que vous restez connecté à votre compte eCandidat sur un autre onglet. Veillez à toujours vous déconnecter !',
    },
  ];
}
