import { ICart } from 'app/shared/model//cart.model';

export interface IItemCart {
    id?: number;
    itemId?: string;
    itemDescription?: string;
    price?: number;
    quantity?: number;
    categoryId?: string;
    categoryDescription?: string;
    cart?: ICart;
}

export class ItemCart implements IItemCart {
    constructor(
        public id?: number,
        public itemId?: string,
        public itemDescription?: string,
        public price?: number,
        public quantity?: number,
        public categoryId?: string,
        public categoryDescription?: string,
        public cart?: ICart
    ) {}
}
