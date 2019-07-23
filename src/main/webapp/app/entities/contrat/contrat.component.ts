import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContrat } from 'app/shared/model/contrat.model';
import { AccountService } from 'app/core';
import { ContratService } from './contrat.service';

@Component({
  selector: 'jhi-contrat',
  templateUrl: './contrat.component.html'
})
export class ContratComponent implements OnInit, OnDestroy {
  contrats: IContrat[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected contratService: ContratService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.contratService
      .query()
      .pipe(
        filter((res: HttpResponse<IContrat[]>) => res.ok),
        map((res: HttpResponse<IContrat[]>) => res.body)
      )
      .subscribe(
        (res: IContrat[]) => {
          this.contrats = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInContrats();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContrat) {
    return item.id;
  }

  registerChangeInContrats() {
    this.eventSubscriber = this.eventManager.subscribe('contratListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
