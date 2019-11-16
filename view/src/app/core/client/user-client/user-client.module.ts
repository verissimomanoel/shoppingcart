import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserClientService } from './user-client.service';

/**
 * User client module.
 * 
 * @author Manoel Veríssimo
 */
@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    UserClientService
  ]
})
export class UserClientModule { }
