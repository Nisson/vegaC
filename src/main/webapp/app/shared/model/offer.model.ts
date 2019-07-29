import { ITransaction } from 'app/shared/model/transaction.model';

export interface IOffer {
  id?: number;
  title?: string;
  data?: number;
  discount?: number;
  maxoffer?: number;
  priceoffer?: number;
  transaction?: ITransaction;
}

export class Offer implements IOffer {
  constructor(
    public id?: number,
    public title?: string,
    public data?: number,
    public discount?: number,
    public maxoffer?: number,
    public priceoffer?: number,
    public transaction?: ITransaction
  ) {}
}
