import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegleCommission } from 'app/shared/model/regle-commission.model';

type EntityResponseType = HttpResponse<IRegleCommission>;
type EntityArrayResponseType = HttpResponse<IRegleCommission[]>;

@Injectable({ providedIn: 'root' })
export class RegleCommissionService {
  public resourceUrl = SERVER_API_URL + 'api/regle-commissions';

  constructor(protected http: HttpClient) {}

  create(regleCommission: IRegleCommission): Observable<EntityResponseType> {
    return this.http.post<IRegleCommission>(this.resourceUrl, regleCommission, { observe: 'response' });
  }

  update(regleCommission: IRegleCommission): Observable<EntityResponseType> {
    return this.http.put<IRegleCommission>(this.resourceUrl, regleCommission, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegleCommission>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegleCommission[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
