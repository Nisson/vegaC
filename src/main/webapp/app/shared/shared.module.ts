import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { VegaCSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [VegaCSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [VegaCSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VegaCSharedModule {
  static forRoot() {
    return {
      ngModule: VegaCSharedModule
    };
  }
}
