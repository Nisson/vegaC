import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmicale } from 'app/shared/model/amicale.model';
import { AmicaleService } from './amicale.service';

@Component({
  selector: 'jhi-amicale-delete-dialog',
  templateUrl: './amicale-delete-dialog.component.html'
})
export class AmicaleDeleteDialogComponent {
  amicale: IAmicale;

  constructor(protected amicaleService: AmicaleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.amicaleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'amicaleListModification',
        content: 'Deleted an amicale'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-amicale-delete-popup',
  template: ''
})
export class AmicaleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amicale }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AmicaleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.amicale = amicale;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/amicale', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/amicale', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
