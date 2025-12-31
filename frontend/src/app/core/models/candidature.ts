export class Candidature {
  constructor(
    public id: string,
    public userId: string,
    public formationId: string,
    public nationality: string,
    public ine: string,
    public address: string,
    public baccalaureate: string,
    public internalExternalCurriculum: string,
    public internships: string,
    public professionalExperience: string,
    public cvUrl: string,
    public status: string = 'En attente',
    public createdAt?: string
  ) {}
}
