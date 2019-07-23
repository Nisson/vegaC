import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { VegaCSharedModule } from 'app/shared';
import {
  AmicaleComponent,
  AmicaleDetailComponent,
  AmicaleUpdateComponent,
  AmicaleDeletePopupComponent,
  AmicaleDeleteDialogComponent,
  amicaleRoute,
  amicalePopupRoute
} from './';

const ENTITY_STATES = [...amicaleRoute, ...amicalePopupRoute];

@NgModule({
  imports: [VegaCSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AmicaleComponent,
    AmicaleDetailComponent,
    AmicaleUpdateComponent,
    AmicaleDeleteDialogComponent,
    AmicaleDeletePopupComponent
  ],
  entryComponents: [AmicaleComponent, AmicaleUpdateComponent, AmicaleDeleteDialogComponent, AmicaleDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VegaCAmicaleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
