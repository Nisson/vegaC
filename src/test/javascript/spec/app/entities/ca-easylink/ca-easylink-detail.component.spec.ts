/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropTestModule } from '../../../test.module';
import { CAEasylinkDetailComponent } from 'app/entities/ca-easylink/ca-easylink-detail.component';
import { CAEasylink } from 'app/shared/model/ca-easylink.model';

describe('Component Tests', () => {
  describe('CAEasylink Management Detail Component', () => {
    let comp: CAEasylinkDetailComponent;
    let fixture: ComponentFixture<CAEasylinkDetailComponent>;
    const route = ({ data: of({ cAEasylink: new CAEasylink(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PropTestModule],
        declarations: [CAEasylinkDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CAEasylinkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CAEasylinkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cAEasylink).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
