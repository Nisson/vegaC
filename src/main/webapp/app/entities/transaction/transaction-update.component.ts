import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { IConvention } from 'app/shared/model/convention.model';
import { ConventionService } from 'app/entities/convention';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html'
})
export class TransactionUpdateComponent implements OnInit {
  isSaving: boolean;

  conventions: IConvention[];
  startdateDp: any;
  enddateDp: any;

  editForm = this.fb.group({
    id: [],
    activationstatus: [],
    address: [],
    amountsubstraction: [],
    startdate: [],
    enddate: [],
    totalamount: [],
    totalpaid: [],
    advancedamount: [],
    convention: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transactionService: TransactionService,
    protected conventionService: ConventionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);
    });
    this.conventionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IConvention[]>) => mayBeOk.ok),
        map((response: HttpResponse<IConvention[]>) => response.body)
      )
      .subscribe((res: IConvention[]) => (this.conventions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transaction: ITransaction) {
    this.editForm.patchValue({
      id: transaction.id,
      activationstatus: transaction.activationstatus,
      address: transaction.address,
      amountsubstraction: transaction.amountsubstraction,
      startdate: transaction.startdate,
      enddate: transaction.enddate,
      totalamount: transaction.totalamount,
      totalpaid: transaction.totalpaid,
      advancedamount: transaction.advancedamount,
      convention: transaction.convention
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id']).value,
      activationstatus: this.editForm.get(['activationstatus']).value,
      address: this.editForm.get(['address']).value,
      amountsubstraction: this.editForm.get(['amountsubstraction']).value,
      startdate: this.editForm.get(['startdate']).value,
      enddate: this.editForm.get(['enddate']).value,
      totalamount: this.editForm.get(['totalamount']).value,
      totalpaid: this.editForm.get(['totalpaid']).value,
      advancedamount: this.editForm.get(['advancedamount']).value,
      convention: this.editForm.get(['convention']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>) {
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

  trackConventionById(index: number, item: IConvention) {
    return item.id;
  }
}
