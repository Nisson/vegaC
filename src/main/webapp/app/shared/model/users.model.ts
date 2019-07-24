export interface IUsers {
  id?: number;
}

export class Users implements IUsers {
  constructor(public id?: number) {}
}
