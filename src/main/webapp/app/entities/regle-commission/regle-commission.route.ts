import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RegleCommission } from 'app/shared/model/regle-commission.model';
import { RegleCommissionService } from './regle-commission.service';
import { RegleCommissionComponent } from './regle-commission.component';
import { RegleCommissionDetailComponent } from './regle-commission-detail.component';
import { RegleCommissionUpdateComponent } from './regle-commission-update.component';
import { RegleCommissionDeletePopupComponent } from './regle-commission-delete-dialog.component';
import { IRegleCommission } from 'app/shared/model/regle-commission.model';

@Injectable({ providedIn: 'root' })
export class RegleCommissionResolve implements Resolve<IRegleCommission> {
  constructor(private service: RegleCommissionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRegleCommission> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RegleCommission>) => response.ok),
        map((regleCommission: HttpResponse<RegleCommission>) => regleCommission.body)
      );
    }
    return of(new RegleCommission());
  }
}

export const regleCommissionRoute: Routes = [
  {
    path: '',
    component: RegleCommissionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'propApp.regleCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegleCommissionDetailComponent,
    resolve: {
      regleCommission: RegleCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'propApp.regleCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegleCommissionUpdateComponent,
    resolve: {
      regleCommission: RegleCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'propApp.regleCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegleCommissionUpdateComponent,
    resolve: {
      regleCommission: RegleCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'propApp.regleCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const regleCommissionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RegleCommissionDeletePopupComponent,
    resolve: {
      regleCommission: RegleCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'propApp.regleCommission.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
