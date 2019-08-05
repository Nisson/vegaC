import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VegaCSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { ChartsModule } from 'ng2-charts';

@NgModule({
  imports: [VegaCSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VegaCHomeModule {}

