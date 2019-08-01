import { IContrat } from 'app/shared/model/contrat.model';

export interface ICAEasylink {
  id?: number;
  gain?: number;
  contrat?: IContrat;
}

export class CAEasylink implements ICAEasylink {
  constructor(public id?: number, public gain?: number, public contrat?: IContrat) {}
}
