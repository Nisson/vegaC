import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConvention } from 'app/shared/model/convention.model';
import { ConventionService } from './convention.service';

@Component({
  selector: 'jhi-convention-delete-dialog',
  templateUrl: './convention-delete-dialog.component.html'
})
export class ConventionDeleteDialogComponent {
  convention: IConvention;

  constructor(
    protected conventionService: ConventionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.conventionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'conventionListModification',
        content: 'Deleted an convention'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-convention-delete-popup',
  template: ''
})
export class ConventionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ convention }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ConventionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.convention = convention;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/convention', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/convention', { outlets: { popup: null } }]);
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
