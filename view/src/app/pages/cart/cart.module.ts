import { NgxMaskModule } from 'ngx-mask'
import { NgModule } from '@angular/core';
import { CartRoutes } from './cart.router';
import { TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';
import { OrderModule } from 'ngx-order-pipe';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UiSwitchModule } from 'ngx-ui-switch';
import { FlexLayoutModule } from '@angular/flex-layout';

import { UIModule } from 'src/app/shared/ui/ui.module';
import { CartFormComponent } from './cart-form/cart-form.component';
import { CartCheckoutComponent } from './cart-checkout/cart-checkout.component';
import { MessageModule } from 'src/app/shared/message/message.module';
import { CartClientModule } from 'src/app/core/client/cart-client/cart-client.module';

/**
 * Cart module.
 * 
 * @author Manoel Veríssimo
 */
@NgModule({
  providers: [
  ],
  declarations: [
    CartFormComponent,
    CartCheckoutComponent
  ],
  imports: [
    UIModule,
    FormsModule,
    OrderModule,
    TableModule,
    CommonModule,
    NgxMaskModule,
    MessageModule,
    UiSwitchModule,
    CartClientModule,
    FlexLayoutModule,
    RouterModule.forChild(CartRoutes)
  ]
})
export class CartModule { }
