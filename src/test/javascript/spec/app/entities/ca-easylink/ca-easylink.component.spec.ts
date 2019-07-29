/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VegaCTestModule } from '../../../test.module';
import { CAEasylinkComponent } from 'app/entities/ca-easylink/ca-easylink.component';
import { CAEasylinkService } from 'app/entities/ca-easylink/ca-easylink.service';
import { CAEasylink } from 'app/shared/model/ca-easylink.model';

describe('Component Tests', () => {
  describe('CAEasylink Management Component', () => {
    let comp: CAEasylinkComponent;
    let fixture: ComponentFixture<CAEasylinkComponent>;
    let service: CAEasylinkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VegaCTestModule],
        declarations: [CAEasylinkComponent],
        providers: []
      })
        .overrideTemplate(CAEasylinkComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CAEasylinkComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CAEasylinkService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CAEasylink(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cAEasylinks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
