import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IContrat, Contrat } from 'app/shared/model/contrat.model';
import { ContratService } from './contrat.service';

@Component({
  selector: 'jhi-contrat-update',
  templateUrl: './contrat-update.component.html'
})
export class ContratUpdateComponent implements OnInit {
  isSaving: boolean;
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
    parPalier: []
  });

  constructor(protected contratService: ContratService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contrat }) => {
      this.updateForm(contrat);
    });
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
      parPalier: contrat.parPalier
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
      parPalier: this.editForm.get(['parPalier']).value
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
}
