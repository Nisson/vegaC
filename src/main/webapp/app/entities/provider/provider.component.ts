import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProvider } from 'app/shared/model/provider.model';
import { AccountService } from 'app/core';
import { ProviderService } from './provider.service';

@Component({
  selector: 'jhi-provider',
  templateUrl: './provider.component.html'
})
export class ProviderComponent implements OnInit, OnDestroy {
  providers: IProvider[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected providerService: ProviderService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.providerService
      .query()
      .pipe(
        filter((res: HttpResponse<IProvider[]>) => res.ok),
        map((res: HttpResponse<IProvider[]>) => res.body)
      )
      .subscribe(
        (res: IProvider[]) => {
          this.providers = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProviders();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProvider) {
    return item.id;
  }

  registerChangeInProviders() {
    this.eventSubscriber = this.eventManager.subscribe('providerListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
