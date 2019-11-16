import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CartClientService } from './cart-client.service';
import { CartResolve } from './cart.resolve';
import { CartCheckoutResolve } from './cart-checkout.resolve';

/**
 * Cart client module.
 * 
 * @author Manoel Veríssimo
 */
@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    CartResolve,
    CartCheckoutResolve,
    CartClientService
  ]
})
export class CartClientModule { }
