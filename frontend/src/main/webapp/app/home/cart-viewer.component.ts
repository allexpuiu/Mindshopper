import { Component, AfterViewInit } from '@angular/core';
import { CartService } from 'app/entities/cart';

@Component({
    selector: 'jhi-cart-viewer',
    templateUrl: './cart-viewer.component.html',
    styleUrls: ['./home.scss']
})
export class CartViewerComponent {
    items: any[];

    constructor(private cartService: CartService) {
        this.items = ['aaaa', 'vvvvvv', 'cccccc'];
        this.getItems();
    }

    getItems() {
        this.cartService.getItems(1).subscribe(rez => {
            this.items = rez.body;
            console.log(this.items);

            setTimeout(() => this.getItems(), 3000);
        });
    }
}
