import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'regle-commission',
        loadChildren: './regle-commission/regle-commission.module#VegaCRegleCommissionModule'
      },
      {
        path: 'contrat',
        loadChildren: './contrat/contrat.module#VegaCContratModule'
      },
      {
        path: 'ca-easylink',
        loadChildren: './ca-easylink/ca-easylink.module#VegaCCAEasylinkModule'
      },
      {
        path: 'transaction',
        loadChildren: './transaction/transaction.module#VegaCTransactionModule'
      },
      {
        path: 'offer',
        loadChildren: './offer/offer.module#VegaCOfferModule'
      },
      {
        path: 'amicale',
        loadChildren: './amicale/amicale.module#VegaCAmicaleModule'
      },
      {
        path: 'provider',
        loadChildren: './provider/provider.module#VegaCProviderModule'
      },
      {
        path: 'convention',
        loadChildren: './convention/convention.module#VegaCConventionModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VegaCEntityModule {}
