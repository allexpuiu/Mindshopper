import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItemCart } from 'app/shared/model/item-cart.model';

type EntityResponseType = HttpResponse<IItemCart>;
type EntityArrayResponseType = HttpResponse<IItemCart[]>;

@Injectable({ providedIn: 'root' })
export class ItemCartService {
    public resourceUrl = SERVER_API_URL + 'api/item-carts';

    constructor(protected http: HttpClient) {}

    create(itemCart: IItemCart): Observable<EntityResponseType> {
        return this.http.post<IItemCart>(this.resourceUrl, itemCart, { observe: 'response' });
    }

    update(itemCart: IItemCart): Observable<EntityResponseType> {
        return this.http.put<IItemCart>(this.resourceUrl, itemCart, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IItemCart>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IItemCart[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
