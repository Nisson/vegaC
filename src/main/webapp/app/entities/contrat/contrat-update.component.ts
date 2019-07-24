import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IContrat, Contrat } from 'app/shared/model/contrat.model';
import { ContratService } from './contrat.service';
import { IConvention } from 'app/shared/model/convention.model';
import { ConventionService } from 'app/entities/convention';

@Component({
  selector: 'jhi-contrat-update',
  templateUrl: './contrat-update.component.html'
})
export class ContratUpdateComponent implements OnInit {
  isSaving: boolean;

  conventions: IConvention[];
  datedebDp: any;
  datefinDp: any;

  editForm = this.fb.group({
    id: [],
    datedeb: [],
    datefin: [],
    typePeriode: [],
    typeCommission: [],
    typeMontant: [],
    montantCommission: [],
    parPalier: [],
    convention: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contratService: ContratService,
    protected conventionService: ConventionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contrat }) => {
      this.updateForm(contrat);
    });
    this.conventionService
      .query({ filter: 'contrat-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IConvention[]>) => mayBeOk.ok),
        map((response: HttpResponse<IConvention[]>) => response.body)
      )
      .subscribe(
        (res: IConvention[]) => {
          if (!this.editForm.get('convention').value || !this.editForm.get('convention').value.id) {
            this.conventions = res;
          } else {
            this.conventionService
              .find(this.editForm.get('convention').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IConvention>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IConvention>) => subResponse.body)
              )
              .subscribe(
                (subRes: IConvention) => (this.conventions = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(contrat: IContrat) {
    this.editForm.patchValue({
      id: contrat.id,
      datedeb: contrat.datedeb,
      datefin: contrat.datefin,
      typePeriode: contrat.typePeriode,
      typeCommission: contrat.typeCommission,
      typeMontant: contrat.typeMontant,
      montantCommission: contrat.montantCommission,
      parPalier: contrat.parPalier,
      convention: contrat.convention
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contrat = this.createFromForm();
    if (contrat.id !== undefined) {
      this.subscribeToSaveResponse(this.contratService.update(contrat));
    } else {
      this.subscribeToSaveResponse(this.contratService.create(contrat));
    }
  }

  private createFromForm(): IContrat {
    return {
      ...new Contrat(),
      id: this.editForm.get(['id']).value,
      datedeb: this.editForm.get(['datedeb']).value,
      datefin: this.editForm.get(['datefin']).value,
      typePeriode: this.editForm.get(['typePeriode']).value,
      typeCommission: this.editForm.get(['typeCommission']).value,
      typeMontant: this.editForm.get(['typeMontant']).value,
      montantCommission: this.editForm.get(['montantCommission']).value,
      parPalier: this.editForm.get(['parPalier']).value,
      convention: this.editForm.get(['convention']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrat>>) {
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
