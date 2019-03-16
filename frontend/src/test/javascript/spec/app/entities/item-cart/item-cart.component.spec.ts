/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MindShopperTestModule } from '../../../test.module';
import { ItemCartComponent } from 'app/entities/item-cart/item-cart.component';
import { ItemCartService } from 'app/entities/item-cart/item-cart.service';
import { ItemCart } from 'app/shared/model/item-cart.model';

describe('Component Tests', () => {
    describe('ItemCart Management Component', () => {
        let comp: ItemCartComponent;
        let fixture: ComponentFixture<ItemCartComponent>;
        let service: ItemCartService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MindShopperTestModule],
                declarations: [ItemCartComponent],
                providers: []
            })
                .overrideTemplate(ItemCartComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ItemCartComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemCartService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ItemCart(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.itemCarts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
