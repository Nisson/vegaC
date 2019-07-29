import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Convention } from 'app/shared/model/convention.model';
import { ConventionService } from './convention.service';
import { ConventionComponent } from './convention.component';
import { ConventionDetailComponent } from './convention-detail.component';
import { ConventionUpdateComponent } from './convention-update.component';
import { ConventionDeletePopupComponent } from './convention-delete-dialog.component';
import { IConvention } from 'app/shared/model/convention.model';

@Injectable({ providedIn: 'root' })
export class ConventionResolve implements Resolve<IConvention> {
  constructor(private service: ConventionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConvention> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Convention>) => response.ok),
        map((convention: HttpResponse<Convention>) => convention.body)
      );
    }
    return of(new Convention());
  }
}

export const conventionRoute: Routes = [
  {
    path: '',
    component: ConventionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vegaCApp.convention.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConventionDetailComponent,
    resolve: {
      convention: ConventionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vegaCApp.convention.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConventionUpdateComponent,
    resolve: {
      convention: ConventionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vegaCApp.convention.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConventionUpdateComponent,
    resolve: {
      convention: ConventionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vegaCApp.convention.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const conventionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ConventionDeletePopupComponent,
    resolve: {
      convention: ConventionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vegaCApp.convention.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
