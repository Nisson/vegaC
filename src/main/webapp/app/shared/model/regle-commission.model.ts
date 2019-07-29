import { IContrat } from 'app/shared/model/contrat.model';

export const enum TypeMontant {
  POURCENTAGE = 'POURCENTAGE',
  DT = 'DT'
}

export const enum TypeCommission {
  FORFAITAIREPARTRANSACTION = 'FORFAITAIREPARTRANSACTION',
  FORFAITAIREPARPERIODE = 'FORFAITAIREPARPERIODE',
  POURCENTAGECA = 'POURCENTAGECA'
}

export interface IRegleCommission {
  id?: number;
  minCA?: number;
  maxCa?: number;
  typeMontant?: TypeMontant;
  montantregle?: number;
  typeCommission?: TypeCommission;
  contrat?: IContrat;
}

export class RegleCommission implements IRegleCommission {
  constructor(
    public id?: number,
    public minCA?: number,
    public maxCa?: number,
    public typeMontant?: TypeMontant,
    public montantregle?: number,
    public typeCommission?: TypeCommission,
    public contrat?: IContrat
  ) {}
}
