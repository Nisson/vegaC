/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VegaCTestModule } from '../../../test.module';
import { ConventionDeleteDialogComponent } from 'app/entities/convention/convention-delete-dialog.component';
import { ConventionService } from 'app/entities/convention/convention.service';

describe('Component Tests', () => {
  describe('Convention Management Delete Component', () => {
    let comp: ConventionDeleteDialogComponent;
    let fixture: ComponentFixture<ConventionDeleteDialogComponent>;
    let service: ConventionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VegaCTestModule],
        declarations: [ConventionDeleteDialogComponent]
      })
        .overrideTemplate(ConventionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConventionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConventionService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
