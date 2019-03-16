import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MindShopperCartModule } from './cart/cart.module';
import { MindShopperItemCartModule } from './item-cart/item-cart.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MindShopperCartModule,
        MindShopperItemCartModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MindShopperEntityModule {}
