import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UIModule } from '../shared/ui/ui.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PagesRoutingModule } from './pages-routing.module';

@NgModule({
  declarations: [DashboardComponent],
  imports: [
    CommonModule,
    UIModule,
    PagesRoutingModule,
  ]
})
export class PagesModule { }
