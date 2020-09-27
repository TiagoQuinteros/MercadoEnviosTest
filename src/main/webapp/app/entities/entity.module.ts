import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'shipment',
        loadChildren: () => import('./shipment/shipment.module').then(m => m.MercadoEnviosTestShipmentModule),
      },
      {
        path: 'checkpoint',
        loadChildren: () => import('./checkpoint/checkpoint.module').then(m => m.MercadoEnviosTestCheckpointModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MercadoEnviosTestEntityModule {}
