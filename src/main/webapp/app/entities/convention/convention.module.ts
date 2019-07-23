import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PropSharedModule } from 'app/shared';
import {
  ConventionComponent,
  ConventionDetailComponent,
  ConventionUpdateComponent,
  ConventionDeletePopupComponent,
  ConventionDeleteDialogComponent,
  conventionRoute,
  conventionPopupRoute
} from './';

const ENTITY_STATES = [...conventionRoute, ...conventionPopupRoute];

@NgModule({
  imports: [PropSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ConventionComponent,
    ConventionDetailComponent,
    ConventionUpdateComponent,
    ConventionDeleteDialogComponent,
    ConventionDeletePopupComponent
  ],
  entryComponents: [ConventionComponent, ConventionUpdateComponent, ConventionDeleteDialogComponent, ConventionDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropConventionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
