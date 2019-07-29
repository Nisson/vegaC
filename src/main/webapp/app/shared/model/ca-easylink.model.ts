import { IContrat } from 'app/shared/model/contrat.model';

export const enum TypePeriode {
  JOURNALIERE = 'JOURNALIERE',
  HEBDOMADAIRE = 'HEBDOMADAIRE',
  MENSUELLE = 'MENSUELLE',
  TRIMESTRIELLE = 'TRIMESTRIELLE',
  SEMESTRIELLE = 'SEMESTRIELLE',
  ANNUELLE = 'ANNUELLE'
}

export interface ICAEasylink {
  id?: number;
  gain?: number;
  typePeriode?: TypePeriode;
  contrat?: IContrat;
}

export class CAEasylink implements ICAEasylink {
  constructor(public id?: number, public gain?: number, public typePeriode?: TypePeriode, public contrat?: IContrat) {}
}
