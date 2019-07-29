import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICAEasylink } from 'app/shared/model/ca-easylink.model';
import { AccountService } from 'app/core';
import { CAEasylinkService } from './ca-easylink.service';

@Component({
  selector: 'jhi-ca-easylink',
  templateUrl: './ca-easylink.component.html'
})
export class CAEasylinkComponent implements OnInit, OnDestroy {
  cAEasylinks: ICAEasylink[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected cAEasylinkService: CAEasylinkService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.cAEasylinkService
      .query()
      .pipe(
        filter((res: HttpResponse<ICAEasylink[]>) => res.ok),
        map((res: HttpResponse<ICAEasylink[]>) => res.body)
      )
      .subscribe(
        (res: ICAEasylink[]) => {
          this.cAEasylinks = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCAEasylinks();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICAEasylink) {
    return item.id;
  }

  registerChangeInCAEasylinks() {
    this.eventSubscriber = this.eventManager.subscribe('cAEasylinkListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
