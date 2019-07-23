import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICAEasylink } from 'app/shared/model/ca-easylink.model';
import { CAEasylinkService } from './ca-easylink.service';

@Component({
  selector: 'jhi-ca-easylink-delete-dialog',
  templateUrl: './ca-easylink-delete-dialog.component.html'
})
export class CAEasylinkDeleteDialogComponent {
  cAEasylink: ICAEasylink;

  constructor(
    protected cAEasylinkService: CAEasylinkService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cAEasylinkService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cAEasylinkListModification',
        content: 'Deleted an cAEasylink'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ca-easylink-delete-popup',
  template: ''
})
export class CAEasylinkDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cAEasylink }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CAEasylinkDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cAEasylink = cAEasylink;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ca-easylink', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ca-easylink', { outlets: { popup: null } }]);
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
