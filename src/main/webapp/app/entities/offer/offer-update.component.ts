import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOffer, Offer } from 'app/shared/model/offer.model';
import { OfferService } from './offer.service';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction';

@Component({
  selector: 'jhi-offer-update',
  templateUrl: './offer-update.component.html'
})
export class OfferUpdateComponent implements OnInit {
  isSaving: boolean;

  transactions: ITransaction[];

  editForm = this.fb.group({
    id: [],
    title: [],
    data: [],
    discount: [],
    maxoffer: [],
    priceoffer: [],
    transaction: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected offerService: OfferService,
    protected transactionService: TransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ offer }) => {
      this.updateForm(offer);
    });
    this.transactionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransaction[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransaction[]>) => response.body)
      )
      .subscribe((res: ITransaction[]) => (this.transactions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(offer: IOffer) {
    this.editForm.patchValue({
      id: offer.id,
      title: offer.title,
      data: offer.data,
      discount: offer.discount,
      maxoffer: offer.maxoffer,
      priceoffer: offer.priceoffer,
      transaction: offer.transaction
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const offer = this.createFromForm();
    if (offer.id !== undefined) {
      this.subscribeToSaveResponse(this.offerService.update(offer));
    } else {
      this.subscribeToSaveResponse(this.offerService.create(offer));
    }
  }

  private createFromForm(): IOffer {
    return {
      ...new Offer(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      data: this.editForm.get(['data']).value,
      discount: this.editForm.get(['discount']).value,
      maxoffer: this.editForm.get(['maxoffer']).value,
      priceoffer: this.editForm.get(['priceoffer']).value,
      transaction: this.editForm.get(['transaction']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffer>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTransactionById(index: number, item: ITransaction) {
    return item.id;
  }
}
