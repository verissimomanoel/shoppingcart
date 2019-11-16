import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { SecurityService } from 'src/app/core/security/security.service';

/**
 * Cart checkout form component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'cart-checkout',
  templateUrl: './cart-checkout.component.html',
  styleUrls: ['./cart-checkout.component.scss']
})
export class CartCheckoutComponent implements OnInit {
  public cart: any;
  public totalItems: number = 0;

  /**
   * Constructor of class.
   * @param route 
   * @param securityService 
   */
  constructor(
    private route: ActivatedRoute,
    public securityService: SecurityService,
  ) { }

  /**
   * Init component.
   */
  ngOnInit() {
    this.cart = this.route.snapshot.data['cart'];
    this.initQtyItem();
  }

  /**
   * Calculate the item quantity in cart.
   */
  private initQtyItem() {
    this.totalItems = 0;
    if (this.cart !== undefined) {
      this.cart.items.forEach(item => {
        this.totalItems = this.totalItems + item.quantity;
      });
    }
  }
}
