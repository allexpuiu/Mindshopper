import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MindShopperSharedModule } from 'app/shared';
import { MindShopperAdminModule } from 'app/admin/admin.module';
import {
    CartComponent,
    CartDetailComponent,
    CartUpdateComponent,
    CartDeletePopupComponent,
    CartDeleteDialogComponent,
    cartRoute,
    cartPopupRoute
} from './';

const ENTITY_STATES = [...cartRoute, ...cartPopupRoute];

@NgModule({
    imports: [MindShopperSharedModule, MindShopperAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CartComponent, CartDetailComponent, CartUpdateComponent, CartDeleteDialogComponent, CartDeletePopupComponent],
    entryComponents: [CartComponent, CartUpdateComponent, CartDeleteDialogComponent, CartDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MindShopperCartModule {}
