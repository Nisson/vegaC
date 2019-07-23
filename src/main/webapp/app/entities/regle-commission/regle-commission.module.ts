import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { VegaCSharedModule } from 'app/shared';
import {
  RegleCommissionComponent,
  RegleCommissionDetailComponent,
  RegleCommissionUpdateComponent,
  RegleCommissionDeletePopupComponent,
  RegleCommissionDeleteDialogComponent,
  regleCommissionRoute,
  regleCommissionPopupRoute
} from './';

const ENTITY_STATES = [...regleCommissionRoute, ...regleCommissionPopupRoute];

@NgModule({
  imports: [VegaCSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RegleCommissionComponent,
    RegleCommissionDetailComponent,
    RegleCommissionUpdateComponent,
    RegleCommissionDeleteDialogComponent,
    RegleCommissionDeletePopupComponent
  ],
  entryComponents: [
    RegleCommissionComponent,
    RegleCommissionUpdateComponent,
    RegleCommissionDeleteDialogComponent,
    RegleCommissionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VegaCRegleCommissionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
