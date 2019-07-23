import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'regle-commission',
        loadChildren: './regle-commission/regle-commission.module#PropRegleCommissionModule'
      },
      {
        path: 'contrat',
        loadChildren: './contrat/contrat.module#PropContratModule'
      },
      {
        path: 'ca-easylink',
        loadChildren: './ca-easylink/ca-easylink.module#PropCAEasylinkModule'
      },
      {
        path: 'transaction',
        loadChildren: './transaction/transaction.module#PropTransactionModule'
      },
      {
        path: 'offer',
        loadChildren: './offer/offer.module#PropOfferModule'
      },
      {
        path: 'amicale',
        loadChildren: './amicale/amicale.module#PropAmicaleModule'
      },
      {
        path: 'provider',
        loadChildren: './provider/provider.module#PropProviderModule'
      },
      {
        path: 'users',
        loadChildren: './users/users.module#PropUsersModule'
      },
      {
        path: 'convention',
        loadChildren: './convention/convention.module#PropConventionModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropEntityModule {}
