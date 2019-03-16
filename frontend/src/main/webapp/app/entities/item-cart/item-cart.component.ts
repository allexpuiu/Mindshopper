import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IItemCart } from 'app/shared/model/item-cart.model';
import { AccountService } from 'app/core';
import { ItemCartService } from './item-cart.service';

@Component({
    selector: 'jhi-item-cart',
    templateUrl: './item-cart.component.html'
})
export class ItemCartComponent implements OnInit, OnDestroy {
    itemCarts: IItemCart[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected itemCartService: ItemCartService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.itemCartService.query().subscribe(
            (res: HttpResponse<IItemCart[]>) => {
                this.itemCarts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInItemCarts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IItemCart) {
        return item.id;
    }

    registerChangeInItemCarts() {
        this.eventSubscriber = this.eventManager.subscribe('itemCartListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
