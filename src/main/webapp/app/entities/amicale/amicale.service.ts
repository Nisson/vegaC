import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmicale } from 'app/shared/model/amicale.model';

type EntityResponseType = HttpResponse<IAmicale>;
type EntityArrayResponseType = HttpResponse<IAmicale[]>;

@Injectable({ providedIn: 'root' })
export class AmicaleService {
  public resourceUrl = SERVER_API_URL + 'api/amicales';

  constructor(protected http: HttpClient) {}

  create(amicale: IAmicale): Observable<EntityResponseType> {
    return this.http.post<IAmicale>(this.resourceUrl, amicale, { observe: 'response' });
  }

  update(amicale: IAmicale): Observable<EntityResponseType> {
    return this.http.put<IAmicale>(this.resourceUrl, amicale, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAmicale>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAmicale[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
