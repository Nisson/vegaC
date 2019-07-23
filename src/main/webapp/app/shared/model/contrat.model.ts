import { Moment } from 'moment';
import { IRegleCommission } from 'app/shared/model/regle-commission.model';
import { ICAEasylink } from 'app/shared/model/ca-easylink.model';

export const enum TypePeriode {
  JOURNALIERE = 'JOURNALIERE',
  HEBDOMADAIRE = 'HEBDOMADAIRE',
  MENSUELLE = 'MENSUELLE',
  TRIMESTRIELLE = 'TRIMESTRIELLE',
  SEMESTRIELLE = 'SEMESTRIELLE',
  ANNUELLE = 'ANNUELLE'
}

export const enum TypeCommission {
  FORFAITAIREPARTRANSACTION = 'FORFAITAIREPARTRANSACTION',
  FORFAITAIREPARPERIODE = 'FORFAITAIREPARPERIODE',
  POURCENTAGECA = 'POURCENTAGECA'
}

export const enum TypeMontant {
  POURCENTAGE = 'POURCENTAGE',
  DT = 'DT'
}

export interface IContrat {
  id?: number;
  datedeb?: Moment;
  datefin?: Moment;
  typePeriode?: TypePeriode;
  typeCommission?: TypeCommission;
  typeMontant?: TypeMontant;
  montantCommission?: number;
  parPalier?: boolean;
  regleCommissions?: IRegleCommission[];
  cAEasylinks?: ICAEasylink[];
}

export class Contrat implements IContrat {
  constructor(
    public id?: number,
    public datedeb?: Moment,
    public datefin?: Moment,
    public typePeriode?: TypePeriode,
    public typeCommission?: TypeCommission,
    public typeMontant?: TypeMontant,
    public montantCommission?: number,
    public parPalier?: boolean,
    public regleCommissions?: IRegleCommission[],
    public cAEasylinks?: ICAEasylink[]
  ) {
    this.parPalier = this.parPalier || false;
  }
}
