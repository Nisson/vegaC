/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PropTestModule } from '../../../test.module';
import { RegleCommissionUpdateComponent } from 'app/entities/regle-commission/regle-commission-update.component';
import { RegleCommissionService } from 'app/entities/regle-commission/regle-commission.service';
import { RegleCommission } from 'app/shared/model/regle-commission.model';

describe('Component Tests', () => {
  describe('RegleCommission Management Update Component', () => {
    let comp: RegleCommissionUpdateComponent;
    let fixture: ComponentFixture<RegleCommissionUpdateComponent>;
    let service: RegleCommissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PropTestModule],
        declarations: [RegleCommissionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RegleCommissionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegleCommissionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegleCommissionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegleCommission(123);
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
        const entity = new RegleCommission();
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
