/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MindShopperTestModule } from '../../../test.module';
import { ItemCartDeleteDialogComponent } from 'app/entities/item-cart/item-cart-delete-dialog.component';
import { ItemCartService } from 'app/entities/item-cart/item-cart.service';

describe('Component Tests', () => {
    describe('ItemCart Management Delete Component', () => {
        let comp: ItemCartDeleteDialogComponent;
        let fixture: ComponentFixture<ItemCartDeleteDialogComponent>;
        let service: ItemCartService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MindShopperTestModule],
                declarations: [ItemCartDeleteDialogComponent]
            })
                .overrideTemplate(ItemCartDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemCartDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemCartService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
