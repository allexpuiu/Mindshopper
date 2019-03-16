/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MindShopperTestModule } from '../../../test.module';
import { ItemCartDetailComponent } from 'app/entities/item-cart/item-cart-detail.component';
import { ItemCart } from 'app/shared/model/item-cart.model';

describe('Component Tests', () => {
    describe('ItemCart Management Detail Component', () => {
        let comp: ItemCartDetailComponent;
        let fixture: ComponentFixture<ItemCartDetailComponent>;
        const route = ({ data: of({ itemCart: new ItemCart(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MindShopperTestModule],
                declarations: [ItemCartDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ItemCartDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemCartDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.itemCart).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
