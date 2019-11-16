import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { Resolve, ActivatedRouteSnapshot, Router } from "@angular/router";

import { MessageService } from '../../../shared/message/message.service';
import { CartClientService } from './cart-client.service';
import { SecurityService } from '../../security/security.service';

/**
 * Cart checkout resolve.
 *
 * @author Manoel Veríssimo
 */
@Injectable()
export class CartCheckoutResolve implements Resolve<any> {

  /**
   * Constructor of class.
   * 
   * @param router 
   * @param messageService 
   * @param pserClientService 
   */
  constructor(
    private router: Router,
    private messageService: MessageService,
    private cartClientService: CartClientService,
    public securityService: SecurityService
  ) { }

  /**
   * @param route 
   */
  resolve(route: ActivatedRouteSnapshot): Observable<any> {
    let cartId: string = route.params.cartId;

    return new Observable(observer => {
      this.cartClientService.checkout(cartId).subscribe((data: any) => {
        observer.next(data);
        observer.complete();
      }, error => {
        if (error.status != 404) {
          observer.error(error);
          this.router.navigate([""]);
          this.messageService.addMsgDanger(error);
        } else {
          observer.next();
          observer.complete();
        }
      });
    });
  }
}
