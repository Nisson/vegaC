import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRegleCommission, RegleCommission } from 'app/shared/model/regle-commission.model';
import { RegleCommissionService } from './regle-commission.service';
import { IContrat } from 'app/shared/model/contrat.model';
import { ContratService } from 'app/entities/contrat';

@Component({
  selector: 'jhi-regle-commission-update',
  templateUrl: './regle-commission-update.component.html'
})
export class RegleCommissionUpdateComponent implements OnInit {
  isSaving: boolean;

  contrats: IContrat[];

  editForm = this.fb.group({
    id: [],
    minCA: [],
    maxCa: [],
    typeMontant: [],
    montantregle: [],
    contrat: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected regleCommissionService: RegleCommissionService,
    protected contratService: ContratService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ regleCommission }) => {
      this.updateForm(regleCommission);
    });
    this.contratService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IContrat[]>) => mayBeOk.ok),
        map((response: HttpResponse<IContrat[]>) => response.body)
      )
      .subscribe((res: IContrat[]) => (this.contrats = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(regleCommission: IRegleCommission) {
    this.editForm.patchValue({
      id: regleCommission.id,
      minCA: regleCommission.minCA,
      maxCa: regleCommission.maxCa,
      typeMontant: regleCommission.typeMontant,
      montantregle: regleCommission.montantregle,
      contrat: regleCommission.contrat
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const regleCommission = this.createFromForm();
    if (regleCommission.id !== undefined) {
      this.subscribeToSaveResponse(this.regleCommissionService.update(regleCommission));
    } else {
      this.subscribeToSaveResponse(this.regleCommissionService.create(regleCommission));
    }
  }

  private createFromForm(): IRegleCommission {
    return {
      ...new RegleCommission(),
      id: this.editForm.get(['id']).value,
      minCA: this.editForm.get(['minCA']).value,
      maxCa: this.editForm.get(['maxCa']).value,
      typeMontant: this.editForm.get(['typeMontant']).value,
      montantregle: this.editForm.get(['montantregle']).value,
      contrat: this.editForm.get(['contrat']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegleCommission>>) {
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

  trackContratById(index: number, item: IContrat) {
    return item.id;
  }
}
