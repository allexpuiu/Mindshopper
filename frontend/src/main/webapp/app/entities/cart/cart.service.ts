import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICart } from 'app/shared/model/cart.model';
import { ItemCart, IItemCart } from 'app/shared/model/item-cart.model';

type EntityResponseType = HttpResponse<ICart>;
type EntityArrayResponseType = HttpResponse<ICart[]>;

@Injectable({ providedIn: 'root' })
export class CartService {
    public resourceUrl = SERVER_API_URL + 'api/carts';

    constructor(protected http: HttpClient) {}

    create(cart: ICart): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cart);
        return this.http
            .post<ICart>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(cart: ICart): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cart);
        return this.http
            .put<ICart>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICart>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICart[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    getItems(id: number): Observable<HttpResponse<IItemCart[]>> {
        return this.http.get<IItemCart[]>(`${this.resourceUrl}/${id}/items`, { observe: 'response' });
    }

    protected convertDateFromClient(cart: ICart): ICart {
        const copy: ICart = Object.assign({}, cart, {
            dateCreated: cart.dateCreated != null && cart.dateCreated.isValid() ? cart.dateCreated.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((cart: ICart) => {
                cart.dateCreated = cart.dateCreated != null ? moment(cart.dateCreated) : null;
            });
        }
        return res;
    }
}
