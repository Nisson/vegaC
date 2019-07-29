/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VegaCTestModule } from '../../../test.module';
import { ConventionDetailComponent } from 'app/entities/convention/convention-detail.component';
import { Convention } from 'app/shared/model/convention.model';

describe('Component Tests', () => {
  describe('Convention Management Detail Component', () => {
    let comp: ConventionDetailComponent;
    let fixture: ComponentFixture<ConventionDetailComponent>;
    const route = ({ data: of({ convention: new Convention(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VegaCTestModule],
        declarations: [ConventionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ConventionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConventionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.convention).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
