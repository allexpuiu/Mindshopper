import { NgModule, CUSTOM_ELEMENTS_SCHEMA, Pipe } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MindShopperSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent, ChatWindowComponent, CartViewerComponent } from './';
import { SafePipe } from './safe.pipe';

@NgModule({
    imports: [MindShopperSharedModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent, ChatWindowComponent, SafePipe, CartViewerComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MindShopperHomeModule {}
