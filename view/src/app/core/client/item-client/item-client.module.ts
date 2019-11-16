import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ItemClientService } from './item-client.service';

/**
 * Item client module.
 * 
 * @author Manoel Veríssimo
 */
@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    ItemClientService
  ]
})
export class ItemClientModule { }
