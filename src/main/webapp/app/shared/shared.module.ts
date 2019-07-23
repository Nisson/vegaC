import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PropSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [PropSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [PropSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropSharedModule {
  static forRoot() {
    return {
      ngModule: PropSharedModule
    };
  }
}
