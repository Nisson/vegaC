import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAmicale, Amicale } from 'app/shared/model/amicale.model';
import { AmicaleService } from './amicale.service';

@Component({
  selector: 'jhi-amicale-update',
  templateUrl: './amicale-update.component.html'
})
export class AmicaleUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    adress: [],
    companyname: [],
    logo: []
  });

  constructor(protected amicaleService: AmicaleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ amicale }) => {
      this.updateForm(amicale);
    });
  }

  updateForm(amicale: IAmicale) {
    this.editForm.patchValue({
      id: amicale.id,
      adress: amicale.adress,
      companyname: amicale.companyname,
      logo: amicale.logo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const amicale = this.createFromForm();
    if (amicale.id !== undefined) {
      this.subscribeToSaveResponse(this.amicaleService.update(amicale));
    } else {
      this.subscribeToSaveResponse(this.amicaleService.create(amicale));
    }
  }

  private createFromForm(): IAmicale {
    return {
      ...new Amicale(),
      id: this.editForm.get(['id']).value,
      adress: this.editForm.get(['adress']).value,
      companyname: this.editForm.get(['companyname']).value,
      logo: this.editForm.get(['logo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmicale>>) {
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
