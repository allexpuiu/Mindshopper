import { Moment } from 'moment';
import { IItemCart } from 'app/shared/model//item-cart.model';
import { IUser } from 'app/core/user/user.model';

export const enum Status {
    NEW = 'NEW',
    COMPLETED = 'COMPLETED'
}

export interface ICart {
    id?: number;
    status?: Status;
    dateCreated?: Moment;
    items?: IItemCart[];
    user?: IUser;
}

export class Cart implements ICart {
    constructor(public id?: number, public status?: Status, public dateCreated?: Moment, public items?: IItemCart[], public user?: IUser) {}
}
