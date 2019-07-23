import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegleCommission } from 'app/shared/model/regle-commission.model';
import { RegleCommissionService } from './regle-commission.service';

@Component({
  selector: 'jhi-regle-commission-delete-dialog',
  templateUrl: './regle-commission-delete-dialog.component.html'
})
export class RegleCommissionDeleteDialogComponent {
  regleCommission: IRegleCommission;

  constructor(
    protected regleCommissionService: RegleCommissionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.regleCommissionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'regleCommissionListModification',
        content: 'Deleted an regleCommission'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-regle-commission-delete-popup',
  template: ''
})
export class RegleCommissionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ regleCommission }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RegleCommissionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.regleCommission = regleCommission;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/regle-commission', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/regle-commission', { outlets: { popup: null } }]);
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
