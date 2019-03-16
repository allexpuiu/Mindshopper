import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICart } from 'app/shared/model/cart.model';
import { CartService } from './cart.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-cart-update',
    templateUrl: './cart-update.component.html'
})
export class CartUpdateComponent implements OnInit {
    cart: ICart;
    isSaving: boolean;

    users: IUser[];
    dateCreated: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected cartService: CartService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cart }) => {
            this.cart = cart;
            this.dateCreated = this.cart.dateCreated != null ? this.cart.dateCreated.format(DATE_TIME_FORMAT) : null;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.cart.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.cart.id !== undefined) {
            this.subscribeToSaveResponse(this.cartService.update(this.cart));
        } else {
            this.subscribeToSaveResponse(this.cartService.create(this.cart));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICart>>) {
        result.subscribe((res: HttpResponse<ICart>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
