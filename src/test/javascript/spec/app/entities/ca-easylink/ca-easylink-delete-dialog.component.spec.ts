/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropTestModule } from '../../../test.module';
import { CAEasylinkDeleteDialogComponent } from 'app/entities/ca-easylink/ca-easylink-delete-dialog.component';
import { CAEasylinkService } from 'app/entities/ca-easylink/ca-easylink.service';

describe('Component Tests', () => {
  describe('CAEasylink Management Delete Component', () => {
    let comp: CAEasylinkDeleteDialogComponent;
    let fixture: ComponentFixture<CAEasylinkDeleteDialogComponent>;
    let service: CAEasylinkService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PropTestModule],
        declarations: [CAEasylinkDeleteDialogComponent]
      })
        .overrideTemplate(CAEasylinkDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CAEasylinkDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CAEasylinkService);
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
