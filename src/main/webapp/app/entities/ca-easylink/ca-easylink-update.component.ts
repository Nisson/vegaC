import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICAEasylink, CAEasylink } from 'app/shared/model/ca-easylink.model';
import { CAEasylinkService } from './ca-easylink.service';
import { IContrat } from 'app/shared/model/contrat.model';
import { ContratService } from 'app/entities/contrat';

@Component({
  selector: 'jhi-ca-easylink-update',
  templateUrl: './ca-easylink-update.component.html'
})
export class CAEasylinkUpdateComponent implements OnInit {
  isSaving: boolean;

  contrats: IContrat[];

  editForm = this.fb.group({
    id: [],
    gain: [],
    typePeriode: [],
    contrat: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cAEasylinkService: CAEasylinkService,
    protected contratService: ContratService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cAEasylink }) => {
      this.updateForm(cAEasylink);
    });
    this.contratService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IContrat[]>) => mayBeOk.ok),
        map((response: HttpResponse<IContrat[]>) => response.body)
      )
      .subscribe((res: IContrat[]) => (this.contrats = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cAEasylink: ICAEasylink) {
    this.editForm.patchValue({
      id: cAEasylink.id,
      gain: cAEasylink.gain,
      typePeriode: cAEasylink.typePeriode,
      contrat: cAEasylink.contrat
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cAEasylink = this.createFromForm();
    if (cAEasylink.id !== undefined) {
      this.subscribeToSaveResponse(this.cAEasylinkService.update(cAEasylink));
    } else {
      this.subscribeToSaveResponse(this.cAEasylinkService.create(cAEasylink));
    }
  }

  private createFromForm(): ICAEasylink {
    return {
      ...new CAEasylink(),
      id: this.editForm.get(['id']).value,
      gain: this.editForm.get(['gain']).value,
      typePeriode: this.editForm.get(['typePeriode']).value,
      contrat: this.editForm.get(['contrat']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICAEasylink>>) {
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
