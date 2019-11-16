import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

/**
 * Cart client service.
 * 
 * @author Manoel Veríssimo
 */
@Injectable({
  providedIn: 'root'
})
export class CartClientService {

  /**
   * Constructor classe.
   * 
   * @param http 
   */
  constructor(private http: HttpClient) { }

  /**
	 * Create item.
	 * 
	 * @param item
	 * @return
	 */
  public create(item: any): Observable<any> {
    return this.http.post(`${environment.shoppingcart.url}/cart`, item);
  }

	/**
   * Add item on cart.
   * 
   * @param cartAddTO 
   */
  public addItem(cartAddTO: any): Observable<any> {
    return this.http.put(`${environment.shoppingcart.url}/cart/add`, cartAddTO);
  }

	/**
   * Add item on cart.
   * 
   * @param userId
   * @param itemId
   */
  public delItem(userId: string, itemId: string): Observable<any> {
    return this.http.delete(`${environment.shoppingcart.url}/cart/remove/${userId}/${itemId}`);
  }

	/**
	 * Return a cart by user.
	 * 
	 * @param userId
	 * @return
	 */
  public search(userId: string): Observable<any> {
    return this.http.get(`${environment.shoppingcart.url}/cart/userId/${userId}`);
  }

  	/**
	 * Return a cart by user.
	 * 
	 * @param filterTO
	 * @return
	 */
  public checkout(cartId: string): Observable<any> {
    return this.http.get(`${environment.shoppingcart.url}/cart/checkout/${cartId}`);
  }
}
