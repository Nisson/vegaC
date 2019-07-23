import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { VegaCSharedModule } from 'app/shared';
import {
  UsersComponent,
  UsersDetailComponent,
  UsersUpdateComponent,
  UsersDeletePopupComponent,
  UsersDeleteDialogComponent,
  usersRoute,
  usersPopupRoute
} from './';

const ENTITY_STATES = [...usersRoute, ...usersPopupRoute];

@NgModule({
  imports: [VegaCSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [UsersComponent, UsersDetailComponent, UsersUpdateComponent, UsersDeleteDialogComponent, UsersDeletePopupComponent],
  entryComponents: [UsersComponent, UsersUpdateComponent, UsersDeleteDialogComponent, UsersDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VegaCUsersModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
