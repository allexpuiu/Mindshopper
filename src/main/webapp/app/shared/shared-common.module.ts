import { NgModule } from '@angular/core';

import { MindShopperSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MindShopperSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MindShopperSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MindShopperSharedCommonModule {}
