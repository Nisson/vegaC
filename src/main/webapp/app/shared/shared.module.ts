import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { VegacSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [VegacSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [VegacSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VegacSharedModule {
  static forRoot() {
    return {
      ngModule: VegacSharedModule
    };
  }
}
