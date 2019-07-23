/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropTestModule } from '../../../test.module';
import { AmicaleComponent } from 'app/entities/amicale/amicale.component';
import { AmicaleService } from 'app/entities/amicale/amicale.service';
import { Amicale } from 'app/shared/model/amicale.model';

describe('Component Tests', () => {
  describe('Amicale Management Component', () => {
    let comp: AmicaleComponent;
    let fixture: ComponentFixture<AmicaleComponent>;
    let service: AmicaleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PropTestModule],
        declarations: [AmicaleComponent],
        providers: []
      })
        .overrideTemplate(AmicaleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmicaleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmicaleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Amicale(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.amicales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
