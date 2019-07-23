import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IConvention, Convention } from 'app/shared/model/convention.model';
import { ConventionService } from './convention.service';
import { IAmicale } from 'app/shared/model/amicale.model';
import { AmicaleService } from 'app/entities/amicale';
import { IProvider } from 'app/shared/model/provider.model';
import { ProviderService } from 'app/entities/provider';
import { IContrat } from 'app/shared/model/contrat.model';
import { ContratService } from 'app/entities/contrat';

@Component({
  selector: 'jhi-convention-update',
  templateUrl: './convention-update.component.html'
})
export class ConventionUpdateComponent implements OnInit {
  isSaving: boolean;

  amicales: IAmicale[];

  providers: IProvider[];

  contrats: IContrat[];
  enddateDp: any;
  startdateDp: any;

  editForm = this.fb.group({
    id: [],
    titleconvention: [],
    amountconvention: [],
    description: [],
    enddate: [],
    startdate: [],
    duration: [],
    totalprice: [],
    amicale: [],
    provider: [],
    contrat: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected conventionService: ConventionService,
    protected amicaleService: AmicaleService,
    protected providerService: ProviderService,
    protected contratService: ContratService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ convention }) => {
      this.updateForm(convention);
    });
    this.amicaleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAmicale[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAmicale[]>) => response.body)
      )
      .subscribe((res: IAmicale[]) => (this.amicales = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.providerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProvider[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProvider[]>) => response.body)
      )
      .subscribe((res: IProvider[]) => (this.providers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.contratService
      .query({ filter: 'convention-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IContrat[]>) => mayBeOk.ok),
        map((response: HttpResponse<IContrat[]>) => response.body)
      )
      .subscribe(
        (res: IContrat[]) => {
          if (!this.editForm.get('contrat').value || !this.editForm.get('contrat').value.id) {
            this.contrats = res;
          } else {
            this.contratService
              .find(this.editForm.get('contrat').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IContrat>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IContrat>) => subResponse.body)
              )
              .subscribe(
                (subRes: IContrat) => (this.contrats = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(convention: IConvention) {
    this.editForm.patchValue({
      id: convention.id,
      titleconvention: convention.titleconvention,
      amountconvention: convention.amountconvention,
      description: convention.description,
      enddate: convention.enddate,
      startdate: convention.startdate,
      duration: convention.duration,
      totalprice: convention.totalprice,
      amicale: convention.amicale,
      provider: convention.provider,
      contrat: convention.contrat
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const convention = this.createFromForm();
    if (convention.id !== undefined) {
      this.subscribeToSaveResponse(this.conventionService.update(convention));
    } else {
      this.subscribeToSaveResponse(this.conventionService.create(convention));
    }
  }

  private createFromForm(): IConvention {
    return {
      ...new Convention(),
      id: this.editForm.get(['id']).value,
      titleconvention: this.editForm.get(['titleconvention']).value,
      amountconvention: this.editForm.get(['amountconvention']).value,
      description: this.editForm.get(['description']).value,
      enddate: this.editForm.get(['enddate']).value,
      startdate: this.editForm.get(['startdate']).value,
      duration: this.editForm.get(['duration']).value,
      totalprice: this.editForm.get(['totalprice']).value,
      amicale: this.editForm.get(['amicale']).value,
      provider: this.editForm.get(['provider']).value,
      contrat: this.editForm.get(['contrat']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConvention>>) {
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

  trackAmicaleById(index: number, item: IAmicale) {
    return item.id;
  }

  trackProviderById(index: number, item: IProvider) {
    return item.id;
  }

  trackContratById(index: number, item: IContrat) {
    return item.id;
  }
}
