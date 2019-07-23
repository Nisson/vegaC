import { IUser } from 'app/core/user/user.model';
import { IConvention } from 'app/shared/model/convention.model';

export interface IUsers {
  id?: number;
  accesslevel?: number;
  cin?: number;
  email?: string;
  firstname?: string;
  lastname?: string;
  login?: string;
  password?: string;
  user?: IUser;
  conventions?: IConvention[];
}

export class Users implements IUsers {
  constructor(
    public id?: number,
    public accesslevel?: number,
    public cin?: number,
    public email?: string,
    public firstname?: string,
    public lastname?: string,
    public login?: string,
    public password?: string,
    public user?: IUser,
    public conventions?: IConvention[]
  ) {}
}
