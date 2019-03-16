import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IItemCart } from 'app/shared/model/item-cart.model';
import { ItemCartService } from './item-cart.service';
import { ICart } from 'app/shared/model/cart.model';
import { CartService } from 'app/entities/cart';

@Component({
    selector: 'jhi-item-cart-update',
    templateUrl: './item-cart-update.component.html'
})
export class ItemCartUpdateComponent implements OnInit {
    itemCart: IItemCart;
    isSaving: boolean;

    carts: ICart[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected itemCartService: ItemCartService,
        protected cartService: CartService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ itemCart }) => {
            this.itemCart = itemCart;
        });
        this.cartService.query().subscribe(
            (res: HttpResponse<ICart[]>) => {
                this.carts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.itemCart.id !== undefined) {
            this.subscribeToSaveResponse(this.itemCartService.update(this.itemCart));
        } else {
            this.subscribeToSaveResponse(this.itemCartService.create(this.itemCart));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemCart>>) {
        result.subscribe((res: HttpResponse<IItemCart>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCartById(index: number, item: ICart) {
        return item.id;
    }
}
