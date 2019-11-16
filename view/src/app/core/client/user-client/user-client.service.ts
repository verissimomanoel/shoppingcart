import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';

/**
 * User client service.
 * 
 * @author Manoel Veríssimo
 */
@Injectable({
  providedIn: 'root'
})
export class UserClientService {

  /**
   * Constructor classe.
   * 
   * @param http 
   */
  constructor(private http: HttpClient) { }

  /**
	 * Create user.
	 * 
	 * @param user
	 * @return
	 */
  public create(user: any): Observable<any> {
    return this.http.post(`${environment.shoppingcart.url}/user`, user);
  }

	/**
   * Update user.
   * 
   * @param user 
   */
  public update(user: any): Observable<any> {
    return this.http.put(`${environment.shoppingcart.url}/user`, user);

  }

	/**
	 * Dekete user.
	 * 
	 * @param id
	 * @return
	 */
  public delete(id: string): Observable<any> {
    return this.http.delete(`${environment.shoppingcart.url}/user/${id}`, {});
  }

	/**
	 * Return the list of user according to the criteria of reported search.
	 * 
	 * @param filterTO
	 * @return
	 */
  public search(filterTO: any): Observable<any> {
    let name = "";
    if (filterTO.name) {
      name = filterTO.name;
    }

    return this.http.get(`${environment.shoppingcart.url}/user/${name}`);
  }

}
