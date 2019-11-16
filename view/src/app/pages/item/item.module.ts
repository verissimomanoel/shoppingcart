import { NgxMaskModule } from 'ngx-mask'
import { NgModule } from '@angular/core';
import { ItemRoutes } from './item.router';
import { TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';
import { OrderModule } from 'ngx-order-pipe';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UiSwitchModule } from 'ngx-ui-switch';
import { FlexLayoutModule } from '@angular/flex-layout';

import { UIModule } from 'src/app/shared/ui/ui.module';
import { ItemFormComponent } from './item-form/item-form.component';
import { ItemListComponent } from './item-list/item-list.component';
import { MessageModule } from 'src/app/shared/message/message.module';
import { ItemDetailComponent } from './item-detail/item-detail.component';
import { ItemClientModule } from 'src/app/core/client/item-client/item-client.module';

/**
 * Item module.
 * 
 * @author Manoel Veríssimo
 */
@NgModule({
  providers: [
  ],
  declarations: [
    ItemFormComponent,
    ItemListComponent,
    ItemDetailComponent
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
    ItemClientModule,
    FlexLayoutModule,
    RouterModule.forChild(ItemRoutes)
  ]
})
export class ItemModule { }
