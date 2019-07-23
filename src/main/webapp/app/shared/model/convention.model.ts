import { Moment } from 'moment';
import { IAmicale } from 'app/shared/model/amicale.model';
import { IProvider } from 'app/shared/model/provider.model';
import { IUsers } from 'app/shared/model/users.model';
import { IContrat } from 'app/shared/model/contrat.model';
import { ITransaction } from 'app/shared/model/transaction.model';

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
  users?: IUsers;
  contrat?: IContrat;
  transactions?: ITransaction[];
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
    public users?: IUsers,
    public contrat?: IContrat,
    public transactions?: ITransaction[]
  ) {}
}
