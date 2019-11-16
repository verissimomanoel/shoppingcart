import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';

/**
 * Auth client service.
 * 
 * @author Manoel Veríssimo
 */
@Injectable({
  providedIn: 'root'
})
export class AuthClientService {

  /**
   * Constructor classe.
   * 
   * @param http 
   */
  constructor(private http: HttpClient) { }

  /**
   * Authenticates the user in applying a temporary access token.
   * 
   * @param authTO 
   */
  public login(authTO: any): Observable<any> {
    return this.http.post(`${environment.shoppingcart.url}/auth/login`, authTO);
  }

	/**
   * Generates a new access token through the refresh token entered.
   * 
   * @param refreshToken 
   */
  public refresh(refreshToken: string): Observable<any> {
    return this.http.post(`${environment.shoppingcart.url}/auth/refresh`, {
      refreshToken: refreshToken
    });
  }
}
