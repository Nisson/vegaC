/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ConventionService } from 'app/entities/convention/convention.service';
import { IConvention, Convention } from 'app/shared/model/convention.model';

describe('Service Tests', () => {
  describe('Convention Service', () => {
    let injector: TestBed;
    let service: ConventionService;
    let httpMock: HttpTestingController;
    let elemDefault: IConvention;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ConventionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Convention(0, 'AAAAAAA', 0, 'AAAAAAA', currentDate, currentDate, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            enddate: currentDate.format(DATE_FORMAT),
            startdate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Convention', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            enddate: currentDate.format(DATE_FORMAT),
            startdate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            enddate: currentDate,
            startdate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Convention(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Convention', async () => {
        const returnedFromService = Object.assign(
          {
            titleconvention: 'BBBBBB',
            amountconvention: 1,
            description: 'BBBBBB',
            enddate: currentDate.format(DATE_FORMAT),
            startdate: currentDate.format(DATE_FORMAT),
            duration: 1,
            totalprice: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            enddate: currentDate,
            startdate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Convention', async () => {
        const returnedFromService = Object.assign(
          {
            titleconvention: 'BBBBBB',
            amountconvention: 1,
            description: 'BBBBBB',
            enddate: currentDate.format(DATE_FORMAT),
            startdate: currentDate.format(DATE_FORMAT),
            duration: 1,
            totalprice: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            enddate: currentDate,
            startdate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Convention', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
