import { Moment } from 'moment';
import { IOffer } from 'app/shared/model/offer.model';
import { IConvention } from 'app/shared/model/convention.model';

export interface ITransaction {
  id?: number;
  activationstatus?: string;
  address?: string;
  amountsubstraction?: number;
  startdate?: Moment;
  enddate?: Moment;
  totalamount?: number;
  totalpaid?: number;
  advancedamount?: number;
  offers?: IOffer[];
  convention?: IConvention;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public activationstatus?: string,
    public address?: string,
    public amountsubstraction?: number,
    public startdate?: Moment,
    public enddate?: Moment,
    public totalamount?: number,
    public totalpaid?: number,
    public advancedamount?: number,
    public offers?: IOffer[],
    public convention?: IConvention
  ) {}
}
