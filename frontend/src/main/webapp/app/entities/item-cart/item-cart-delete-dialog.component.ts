import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemCart } from 'app/shared/model/item-cart.model';
import { ItemCartService } from './item-cart.service';

@Component({
    selector: 'jhi-item-cart-delete-dialog',
    templateUrl: './item-cart-delete-dialog.component.html'
})
export class ItemCartDeleteDialogComponent {
    itemCart: IItemCart;

    constructor(protected itemCartService: ItemCartService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemCartService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'itemCartListModification',
                content: 'Deleted an itemCart'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-cart-delete-popup',
    template: ''
})
export class ItemCartDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemCart }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemCartDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.itemCart = itemCart;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
