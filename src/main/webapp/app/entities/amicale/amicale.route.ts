import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Amicale } from 'app/shared/model/amicale.model';
import { AmicaleService } from './amicale.service';
import { AmicaleComponent } from './amicale.component';
import { AmicaleDetailComponent } from './amicale-detail.component';
import { AmicaleUpdateComponent } from './amicale-update.component';
import { AmicaleDeletePopupComponent } from './amicale-delete-dialog.component';
import { IAmicale } from 'app/shared/model/amicale.model';

@Injectable({ providedIn: 'root' })
export class AmicaleResolve implements Resolve<IAmicale> {
  constructor(private service: AmicaleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmicale> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Amicale>) => response.ok),
        map((amicale: HttpResponse<Amicale>) => amicale.body)
      );
    }
    return of(new Amicale());
  }
}

export const amicaleRoute: Routes = [
  {
    path: '',
    component: AmicaleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vegaCApp.amicale.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AmicaleDetailComponent,
    resolve: {
      amicale: AmicaleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vegaCApp.amicale.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AmicaleUpdateComponent,
    resolve: {
      amicale: AmicaleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vegaCApp.amicale.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AmicaleUpdateComponent,
    resolve: {
      amicale: AmicaleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vegaCApp.amicale.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const amicalePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AmicaleDeletePopupComponent,
    resolve: {
      amicale: AmicaleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'vegaCApp.amicale.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
