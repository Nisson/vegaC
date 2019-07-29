/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { VegaCTestModule } from '../../../test.module';
import { AmicaleUpdateComponent } from 'app/entities/amicale/amicale-update.component';
import { AmicaleService } from 'app/entities/amicale/amicale.service';
import { Amicale } from 'app/shared/model/amicale.model';

describe('Component Tests', () => {
  describe('Amicale Management Update Component', () => {
    let comp: AmicaleUpdateComponent;
    let fixture: ComponentFixture<AmicaleUpdateComponent>;
    let service: AmicaleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VegaCTestModule],
        declarations: [AmicaleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AmicaleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmicaleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmicaleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Amicale(123);
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
        const entity = new Amicale();
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
