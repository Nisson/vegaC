/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { VegaCTestModule } from '../../../test.module';
import { CAEasylinkUpdateComponent } from 'app/entities/ca-easylink/ca-easylink-update.component';
import { CAEasylinkService } from 'app/entities/ca-easylink/ca-easylink.service';
import { CAEasylink } from 'app/shared/model/ca-easylink.model';

describe('Component Tests', () => {
  describe('CAEasylink Management Update Component', () => {
    let comp: CAEasylinkUpdateComponent;
    let fixture: ComponentFixture<CAEasylinkUpdateComponent>;
    let service: CAEasylinkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VegaCTestModule],
        declarations: [CAEasylinkUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CAEasylinkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CAEasylinkUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CAEasylinkService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CAEasylink(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CAEasylink();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
