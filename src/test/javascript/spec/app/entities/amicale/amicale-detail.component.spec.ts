/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropTestModule } from '../../../test.module';
import { AmicaleDetailComponent } from 'app/entities/amicale/amicale-detail.component';
import { Amicale } from 'app/shared/model/amicale.model';

describe('Component Tests', () => {
  describe('Amicale Management Detail Component', () => {
    let comp: AmicaleDetailComponent;
    let fixture: ComponentFixture<AmicaleDetailComponent>;
    const route = ({ data: of({ amicale: new Amicale(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PropTestModule],
        declarations: [AmicaleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AmicaleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmicaleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.amicale).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
