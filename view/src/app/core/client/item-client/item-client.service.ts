import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

/**
 * Item client service.
 * 
 * @author Manoel Veríssimo
 */
@Injectable({
  providedIn: 'root'
})
export class ItemClientService {

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
    return this.http.post(`${environment.shoppingcart.url}/item`, item);
  }

	/**
   * Update item.
   * 
   * @param item 
   */
  public update(item: any): Observable<any> {
    return this.http.put(`${environment.shoppingcart.url}/item`, item);

  }

	/**
	 * Delete item.
	 * 
	 * @param id
	 * @return
	 */
  public delete(id: string): Observable<any> {
    return this.http.delete(`${environment.shoppingcart.url}/item/${id}`, {});
  }

	/**
	 * Return the list of item according to the criteria of reported search.
	 * 
	 * @param filterTO
	 * @return
	 */
  public search(filterTO: any): Observable<any> {
    let name = "";
    if (filterTO.name) {
      name = filterTO.name;
    }

    return this.http.get(`${environment.shoppingcart.url}/item/${name}`);
  }

	/**
	 * Return the all items.
	 * 
	 * @return
	 */
  public listAll(): Observable<any> {
    return this.http.get(`${environment.shoppingcart.url}/item/all`);
  }

}
