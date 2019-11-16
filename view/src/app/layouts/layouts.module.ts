import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { UIModule } from '../shared/ui/ui.module';
import { LayoutComponent } from './layout.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { TopbarComponent } from './topbar/topbar.component';
import { FooterComponent } from './footer/footer.component';
import { MessageModule } from '../shared/message/message.module';


@NgModule({
  declarations: [
    LayoutComponent,
    SidebarComponent,
    TopbarComponent,
    FooterComponent,
  ],
  imports: [
    UIModule,
    CommonModule,
    RouterModule,
    MessageModule,
    BsDropdownModule.forRoot()
  ]
})
export class LayoutsModule { }
