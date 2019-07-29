import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProvider, Provider } from 'app/shared/model/provider.model';
import { ProviderService } from './provider.service';

@Component({
  selector: 'jhi-provider-update',
  templateUrl: './provider-update.component.html'
})
export class ProviderUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    adress: [],
    email: [],
    firstname: [],
    lastname: [],
    logo: []
  });

  constructor(protected providerService: ProviderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ provider }) => {
      this.updateForm(provider);
    });
  }

  updateForm(provider: IProvider) {
    this.editForm.patchValue({
      id: provider.id,
      adress: provider.adress,
      email: provider.email,
      firstname: provider.firstname,
      lastname: provider.lastname,
      logo: provider.logo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const provider = this.createFromForm();
    if (provider.id !== undefined) {
      this.subscribeToSaveResponse(this.providerService.update(provider));
    } else {
      this.subscribeToSaveResponse(this.providerService.create(provider));
    }
  }

  private createFromForm(): IProvider {
    return {
      ...new Provider(),
      id: this.editForm.get(['id']).value,
      adress: this.editForm.get(['adress']).value,
      email: this.editForm.get(['email']).value,
      firstname: this.editForm.get(['firstname']).value,
      lastname: this.editForm.get(['lastname']).value,
      logo: this.editForm.get(['logo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvider>>) {
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
