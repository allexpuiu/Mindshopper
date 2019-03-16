import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MindShopperSharedModule } from 'app/shared';
import {
    ItemCartComponent,
    ItemCartDetailComponent,
    ItemCartUpdateComponent,
    ItemCartDeletePopupComponent,
    ItemCartDeleteDialogComponent,
    itemCartRoute,
    itemCartPopupRoute
} from './';

const ENTITY_STATES = [...itemCartRoute, ...itemCartPopupRoute];

@NgModule({
    imports: [MindShopperSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemCartComponent,
        ItemCartDetailComponent,
        ItemCartUpdateComponent,
        ItemCartDeleteDialogComponent,
        ItemCartDeletePopupComponent
    ],
    entryComponents: [ItemCartComponent, ItemCartUpdateComponent, ItemCartDeleteDialogComponent, ItemCartDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MindShopperItemCartModule {}
