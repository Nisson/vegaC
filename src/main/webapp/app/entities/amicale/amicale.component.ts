import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAmicale } from 'app/shared/model/amicale.model';
import { AccountService } from 'app/core';
import { AmicaleService } from './amicale.service';

@Component({
  selector: 'jhi-amicale',
  templateUrl: './amicale.component.html'
})
export class AmicaleComponent implements OnInit, OnDestroy {
  amicales: IAmicale[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected amicaleService: AmicaleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.amicaleService
      .query()
      .pipe(
        filter((res: HttpResponse<IAmicale[]>) => res.ok),
        map((res: HttpResponse<IAmicale[]>) => res.body)
      )
      .subscribe(
        (res: IAmicale[]) => {
          this.amicales = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAmicales();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAmicale) {
    return item.id;
  }

  registerChangeInAmicales() {
    this.eventSubscriber = this.eventManager.subscribe('amicaleListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
