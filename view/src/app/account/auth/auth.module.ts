import { NgxMaskModule } from 'ngx-mask'
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AuthGuard } from './auth.guard';
import { AuthRoutingModule } from './auth-routing';
import { LoginComponent } from './login/login.component';
import { MessageModule } from 'src/app/shared/message/message.module';

/**
 * Auth module.
 * 
 * @author Manoel Veríssimo
 */
@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
    FormsModule,
    CommonModule,
    NgxMaskModule,
    MessageModule,
    FlexLayoutModule,
    AuthRoutingModule,
    ReactiveFormsModule,
  ],
  providers: [
    AuthGuard
  ]
})
export class AuthModule { }
