import { NgxMaskModule } from 'ngx-mask'
import { NgModule } from '@angular/core';
import { UserRoutes } from './user.router';
import { TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';
import { OrderModule } from 'ngx-order-pipe';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UiSwitchModule } from 'ngx-ui-switch';
import { FlexLayoutModule } from '@angular/flex-layout';

import { UIModule } from 'src/app/shared/ui/ui.module';
import { UserFormComponent } from './user-form/user-form.component';
import { UserListComponent } from './user-list/user-list.component';
import { MessageModule } from 'src/app/shared/message/message.module';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UserClientModule } from 'src/app/core/client/user-client/user-client.module';

/**
 * User module.
 * 
 * @author Manoel Veríssimo
 */
@NgModule({
  providers: [
  ],
  declarations: [
    UserFormComponent,
    UserListComponent,
    UserDetailComponent
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
    UserClientModule,
    FlexLayoutModule,
    RouterModule.forChild(UserRoutes)
  ]
})
export class UserModule { }
