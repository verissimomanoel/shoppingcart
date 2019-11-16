import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SecurityGuard } from '../core/security/security.guard';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    canActivate: [
      SecurityGuard
    ],
  },
  {
    path: 'user',
    loadChildren: () => import('./user/user.module').then(m => m.UserModule)
  },
  {
    path: 'item',
    loadChildren: () => import('./item/item.module').then(m => m.ItemModule)
  },
  {
    path: 'cart',
    loadChildren: () => import('./cart/cart.module').then(m => m.CartModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule { }
