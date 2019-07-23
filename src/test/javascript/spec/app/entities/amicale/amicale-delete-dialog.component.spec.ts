/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropTestModule } from '../../../test.module';
import { AmicaleDeleteDialogComponent } from 'app/entities/amicale/amicale-delete-dialog.component';
import { AmicaleService } from 'app/entities/amicale/amicale.service';

describe('Component Tests', () => {
  describe('Amicale Management Delete Component', () => {
    let comp: AmicaleDeleteDialogComponent;
    let fixture: ComponentFixture<AmicaleDeleteDialogComponent>;
    let service: AmicaleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PropTestModule],
        declarations: [AmicaleDeleteDialogComponent]
      })
        .overrideTemplate(AmicaleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmicaleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmicaleService);
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
