import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConvention } from 'app/shared/model/convention.model';

type EntityResponseType = HttpResponse<IConvention>;
type EntityArrayResponseType = HttpResponse<IConvention[]>;

@Injectable({ providedIn: 'root' })
export class ConventionService {
  public resourceUrl = SERVER_API_URL + 'api/conventions';

  constructor(protected http: HttpClient) {}

  create(convention: IConvention): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(convention);
    return this.http
      .post<IConvention>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(convention: IConvention): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(convention);
    return this.http
      .put<IConvention>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConvention>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConvention[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(convention: IConvention): IConvention {
    const copy: IConvention = Object.assign({}, convention, {
      enddate: convention.enddate != null && convention.enddate.isValid() ? convention.enddate.format(DATE_FORMAT) : null,
      startdate: convention.startdate != null && convention.startdate.isValid() ? convention.startdate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.enddate = res.body.enddate != null ? moment(res.body.enddate) : null;
      res.body.startdate = res.body.startdate != null ? moment(res.body.startdate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((convention: IConvention) => {
        convention.enddate = convention.enddate != null ? moment(convention.enddate) : null;
        convention.startdate = convention.startdate != null ? moment(convention.startdate) : null;
      });
    }
    return res;
  }
}
