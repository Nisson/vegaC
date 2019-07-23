import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConvention } from 'app/shared/model/convention.model';
import { AccountService } from 'app/core';
import { ConventionService } from './convention.service';

@Component({
  selector: 'jhi-convention',
  templateUrl: './convention.component.html'
})
export class ConventionComponent implements OnInit, OnDestroy {
  conventions: IConvention[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected conventionService: ConventionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.conventionService
      .query()
      .pipe(
        filter((res: HttpResponse<IConvention[]>) => res.ok),
        map((res: HttpResponse<IConvention[]>) => res.body)
      )
      .subscribe(
        (res: IConvention[]) => {
          this.conventions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInConventions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IConvention) {
    return item.id;
  }

  registerChangeInConventions() {
    this.eventSubscriber = this.eventManager.subscribe('conventionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
