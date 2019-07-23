import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRegleCommission } from 'app/shared/model/regle-commission.model';
import { AccountService } from 'app/core';
import { RegleCommissionService } from './regle-commission.service';

@Component({
  selector: 'jhi-regle-commission',
  templateUrl: './regle-commission.component.html'
})
export class RegleCommissionComponent implements OnInit, OnDestroy {
  regleCommissions: IRegleCommission[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected regleCommissionService: RegleCommissionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.regleCommissionService
      .query()
      .pipe(
        filter((res: HttpResponse<IRegleCommission[]>) => res.ok),
        map((res: HttpResponse<IRegleCommission[]>) => res.body)
      )
      .subscribe(
        (res: IRegleCommission[]) => {
          this.regleCommissions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRegleCommissions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRegleCommission) {
    return item.id;
  }

  registerChangeInRegleCommissions() {
    this.eventSubscriber = this.eventManager.subscribe('regleCommissionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
