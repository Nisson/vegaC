import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PropSharedModule } from 'app/shared';
import {
  CAEasylinkComponent,
  CAEasylinkDetailComponent,
  CAEasylinkUpdateComponent,
  CAEasylinkDeletePopupComponent,
  CAEasylinkDeleteDialogComponent,
  cAEasylinkRoute,
  cAEasylinkPopupRoute
} from './';

const ENTITY_STATES = [...cAEasylinkRoute, ...cAEasylinkPopupRoute];

@NgModule({
  imports: [PropSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CAEasylinkComponent,
    CAEasylinkDetailComponent,
    CAEasylinkUpdateComponent,
    CAEasylinkDeleteDialogComponent,
    CAEasylinkDeletePopupComponent
  ],
  entryComponents: [CAEasylinkComponent, CAEasylinkUpdateComponent, CAEasylinkDeleteDialogComponent, CAEasylinkDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropCAEasylinkModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
