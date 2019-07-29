import { Moment } from 'moment';
import { IAmicale } from 'app/shared/model/amicale.model';
import { IProvider } from 'app/shared/model/provider.model';
import { ITransaction } from 'app/shared/model/transaction.model';
import { IContrat } from 'app/shared/model/contrat.model';

export interface IConvention {
  id?: number;
  titleconvention?: string;
  amountconvention?: number;
  description?: string;
  enddate?: Moment;
  startdate?: Moment;
  duration?: number;
  totalprice?: number;
  amicale?: IAmicale;
  provider?: IProvider;
  transactions?: ITransaction[];
  contrat?: IContrat;
}

export class Convention implements IConvention {
  constructor(
    public id?: number,
    public titleconvention?: string,
    public amountconvention?: number,
    public description?: string,
    public enddate?: Moment,
    public startdate?: Moment,
    public duration?: number,
    public totalprice?: number,
    public amicale?: IAmicale,
    public provider?: IProvider,
    public transactions?: ITransaction[],
    public contrat?: IContrat
  ) {}
}
