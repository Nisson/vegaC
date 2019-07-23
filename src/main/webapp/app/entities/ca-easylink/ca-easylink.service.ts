import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICAEasylink } from 'app/shared/model/ca-easylink.model';

type EntityResponseType = HttpResponse<ICAEasylink>;
type EntityArrayResponseType = HttpResponse<ICAEasylink[]>;

@Injectable({ providedIn: 'root' })
export class CAEasylinkService {
  public resourceUrl = SERVER_API_URL + 'api/ca-easylinks';

  constructor(protected http: HttpClient) {}

  create(cAEasylink: ICAEasylink): Observable<EntityResponseType> {
    return this.http.post<ICAEasylink>(this.resourceUrl, cAEasylink, { observe: 'response' });
  }

  update(cAEasylink: ICAEasylink): Observable<EntityResponseType> {
    return this.http.put<ICAEasylink>(this.resourceUrl, cAEasylink, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICAEasylink>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICAEasylink[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
