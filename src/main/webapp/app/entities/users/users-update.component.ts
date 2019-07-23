import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUsers, Users } from 'app/shared/model/users.model';
import { UsersService } from './users.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-users-update',
  templateUrl: './users-update.component.html'
})
export class UsersUpdateComponent implements OnInit {
  isSaving: boolean;

  usersCollection: IUser[];

  editForm = this.fb.group({
    id: [],
    accesslevel: [],
    cin: [],
    email: [],
    firstname: [],
    lastname: [],
    login: [],
    password: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected usersService: UsersService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ users }) => {
      this.updateForm(users);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.usersCollection = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(users: IUsers) {
    this.editForm.patchValue({
      id: users.id,
      accesslevel: users.accesslevel,
      cin: users.cin,
      email: users.email,
      firstname: users.firstname,
      lastname: users.lastname,
      login: users.login,
      password: users.password,
      user: users.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const users = this.createFromForm();
    if (users.id !== undefined) {
      this.subscribeToSaveResponse(this.usersService.update(users));
    } else {
      this.subscribeToSaveResponse(this.usersService.create(users));
    }
  }

  private createFromForm(): IUsers {
    return {
      ...new Users(),
      id: this.editForm.get(['id']).value,
      accesslevel: this.editForm.get(['accesslevel']).value,
      cin: this.editForm.get(['cin']).value,
      email: this.editForm.get(['email']).value,
      firstname: this.editForm.get(['firstname']).value,
      lastname: this.editForm.get(['lastname']).value,
      login: this.editForm.get(['login']).value,
      password: this.editForm.get(['password']).value,
      user: this.editForm.get(['user']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsers>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
