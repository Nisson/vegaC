import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PropSharedModule } from 'app/shared';
import {
  OfferComponent,
  OfferDetailComponent,
  OfferUpdateComponent,
  OfferDeletePopupComponent,
  OfferDeleteDialogComponent,
  offerRoute,
  offerPopupRoute
} from './';

const ENTITY_STATES = [...offerRoute, ...offerPopupRoute];

@NgModule({
  imports: [PropSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [OfferComponent, OfferDetailComponent, OfferUpdateComponent, OfferDeleteDialogComponent, OfferDeletePopupComponent],
  entryComponents: [OfferComponent, OfferUpdateComponent, OfferDeleteDialogComponent, OfferDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropOfferModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
