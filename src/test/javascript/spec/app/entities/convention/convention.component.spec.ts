/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VegaCTestModule } from '../../../test.module';
import { ConventionComponent } from 'app/entities/convention/convention.component';
import { ConventionService } from 'app/entities/convention/convention.service';
import { Convention } from 'app/shared/model/convention.model';

describe('Component Tests', () => {
  describe('Convention Management Component', () => {
    let comp: ConventionComponent;
    let fixture: ComponentFixture<ConventionComponent>;
    let service: ConventionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VegaCTestModule],
        declarations: [ConventionComponent],
        providers: []
      })
        .overrideTemplate(ConventionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConventionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConventionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Convention(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conventions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
