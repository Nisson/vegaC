import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CAEasylink } from 'app/shared/model/ca-easylink.model';
import { CAEasylinkService } from './ca-easylink.service';
import { CAEasylinkComponent } from './ca-easylink.component';
import { CAEasylinkDetailComponent } from './ca-easylink-detail.component';
import { CAEasylinkUpdateComponent } from './ca-easylink-update.component';
import { CAEasylinkDeletePopupComponent } from './ca-easylink-delete-dialog.component';
import { ICAEasylink } from 'app/shared/model/ca-easylink.model';

@Injectable({ providedIn: 'root' })
export class CAEasylinkResolve implements Resolve<ICAEasylink> {
  constructor(private service: CAEasylinkService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICAEasylink> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CAEasylink>) => response.ok),
        map((cAEasylink: HttpResponse<CAEasylink>) => cAEasylink.body)
      );
    }
    return of(new CAEasylink());
  }
}

export const cAEasylinkRoute: Routes = [
  {
    path: '',
    component: CAEasylinkComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'propApp.cAEasylink.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CAEasylinkDetailComponent,
    resolve: {
      cAEasylink: CAEasylinkResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'propApp.cAEasylink.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CAEasylinkUpdateComponent,
    resolve: {
      cAEasylink: CAEasylinkResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'propApp.cAEasylink.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CAEasylinkUpdateComponent,
    resolve: {
      cAEasylink: CAEasylinkResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'propApp.cAEasylink.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cAEasylinkPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CAEasylinkDeletePopupComponent,
    resolve: {
      cAEasylink: CAEasylinkResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'propApp.cAEasylink.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
