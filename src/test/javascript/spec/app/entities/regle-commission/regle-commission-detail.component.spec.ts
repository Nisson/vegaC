/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropTestModule } from '../../../test.module';
import { RegleCommissionDetailComponent } from 'app/entities/regle-commission/regle-commission-detail.component';
import { RegleCommission } from 'app/shared/model/regle-commission.model';

describe('Component Tests', () => {
  describe('RegleCommission Management Detail Component', () => {
    let comp: RegleCommissionDetailComponent;
    let fixture: ComponentFixture<RegleCommissionDetailComponent>;
    const route = ({ data: of({ regleCommission: new RegleCommission(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PropTestModule],
        declarations: [RegleCommissionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RegleCommissionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegleCommissionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.regleCommission).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
