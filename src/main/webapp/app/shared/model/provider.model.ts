import { IConvention } from 'app/shared/model/convention.model';

export interface IProvider {
  id?: number;
  adress?: string;
  email?: string;
  firstname?: string;
  lastname?: string;
  logo?: string;
  conventions?: IConvention[];
}

export class Provider implements IProvider {
  constructor(
    public id?: number,
    public adress?: string,
    public email?: string,
    public firstname?: string,
    public lastname?: string,
    public logo?: string,
    public conventions?: IConvention[]
  ) {}
}
