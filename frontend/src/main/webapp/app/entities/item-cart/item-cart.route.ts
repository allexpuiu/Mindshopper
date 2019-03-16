import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemCart } from 'app/shared/model/item-cart.model';
import { ItemCartService } from './item-cart.service';
import { ItemCartComponent } from './item-cart.component';
import { ItemCartDetailComponent } from './item-cart-detail.component';
import { ItemCartUpdateComponent } from './item-cart-update.component';
import { ItemCartDeletePopupComponent } from './item-cart-delete-dialog.component';
import { IItemCart } from 'app/shared/model/item-cart.model';

@Injectable({ providedIn: 'root' })
export class ItemCartResolve implements Resolve<IItemCart> {
    constructor(private service: ItemCartService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ItemCart> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ItemCart>) => response.ok),
                map((itemCart: HttpResponse<ItemCart>) => itemCart.body)
            );
        }
        return of(new ItemCart());
    }
}

export const itemCartRoute: Routes = [
    {
        path: 'item-cart',
        component: ItemCartComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCarts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'item-cart/:id/view',
        component: ItemCartDetailComponent,
        resolve: {
            itemCart: ItemCartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCarts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'item-cart/new',
        component: ItemCartUpdateComponent,
        resolve: {
            itemCart: ItemCartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCarts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'item-cart/:id/edit',
        component: ItemCartUpdateComponent,
        resolve: {
            itemCart: ItemCartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCarts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemCartPopupRoute: Routes = [
    {
        path: 'item-cart/:id/delete',
        component: ItemCartDeletePopupComponent,
        resolve: {
            itemCart: ItemCartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCarts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
