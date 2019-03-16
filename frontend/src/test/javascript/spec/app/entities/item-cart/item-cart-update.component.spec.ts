/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MindShopperTestModule } from '../../../test.module';
import { ItemCartUpdateComponent } from 'app/entities/item-cart/item-cart-update.component';
import { ItemCartService } from 'app/entities/item-cart/item-cart.service';
import { ItemCart } from 'app/shared/model/item-cart.model';

describe('Component Tests', () => {
    describe('ItemCart Management Update Component', () => {
        let comp: ItemCartUpdateComponent;
        let fixture: ComponentFixture<ItemCartUpdateComponent>;
        let service: ItemCartService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MindShopperTestModule],
                declarations: [ItemCartUpdateComponent]
            })
                .overrideTemplate(ItemCartUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ItemCartUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemCartService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ItemCart(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.itemCart = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ItemCart();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.itemCart = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
