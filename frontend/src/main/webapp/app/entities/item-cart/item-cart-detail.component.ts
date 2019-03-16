import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemCart } from 'app/shared/model/item-cart.model';

@Component({
    selector: 'jhi-item-cart-detail',
    templateUrl: './item-cart-detail.component.html'
})
export class ItemCartDetailComponent implements OnInit {
    itemCart: IItemCart;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemCart }) => {
            this.itemCart = itemCart;
        });
    }

    previousState() {
        window.history.back();
    }
}
