import { Routes } from '@angular/router';

import { CartFormComponent } from './cart-form/cart-form.component';
import { SecurityGuard } from 'src/app/core/security/security.guard';
import { CartResolve } from 'src/app/core/client/cart-client/cart.resolve';
import { CartCheckoutComponent } from './cart-checkout/cart-checkout.component';
import { CartCheckoutResolve } from 'src/app/core/client/cart-client/cart-checkout.resolve';

/**
 * Cart routes.
 *
 * @author Manoel Veríssimo
 */
export const CartRoutes: Routes = [
  {
    path: 'create',
    component: CartFormComponent,
    resolve: {
      cart: CartResolve
    },
    data: {
      action: 'create'
    },
    canActivate: [
      SecurityGuard
    ]
  },
  {
    path: 'checkout/:cartId',
    component: CartCheckoutComponent,
    resolve: {
      cart: CartCheckoutResolve
    },
    canActivate: [
      SecurityGuard
    ]
  },
  {
    path: '',
    redirectTo: 'create',
    pathMatch: 'full',
    canActivate: [
      SecurityGuard
    ]
  }
];