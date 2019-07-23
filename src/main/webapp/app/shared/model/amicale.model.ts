import { IConvention } from 'app/shared/model/convention.model';

export interface IAmicale {
  id?: number;
  adress?: string;
  companyname?: string;
  logo?: string;
  conventions?: IConvention[];
}

export class Amicale implements IAmicale {
  constructor(
    public id?: number,
    public adress?: string,
    public companyname?: string,
    public logo?: string,
    public conventions?: IConvention[]
  ) {}
}
