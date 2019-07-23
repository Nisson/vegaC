/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropTestModule } from '../../../test.module';
import { RegleCommissionComponent } from 'app/entities/regle-commission/regle-commission.component';
import { RegleCommissionService } from 'app/entities/regle-commission/regle-commission.service';
import { RegleCommission } from 'app/shared/model/regle-commission.model';

describe('Component Tests', () => {
  describe('RegleCommission Management Component', () => {
    let comp: RegleCommissionComponent;
    let fixture: ComponentFixture<RegleCommissionComponent>;
    let service: RegleCommissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PropTestModule],
        declarations: [RegleCommissionComponent],
        providers: []
      })
        .overrideTemplate(RegleCommissionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegleCommissionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegleCommissionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RegleCommission(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.regleCommissions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
