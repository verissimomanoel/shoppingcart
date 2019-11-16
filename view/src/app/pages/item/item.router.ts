import { Routes } from '@angular/router';

import { ItemFormComponent } from './item-form/item-form.component';
import { ItemListComponent } from './item-list/item-list.component';
import { SecurityGuard } from 'src/app/core/security/security.guard';
import { ItemDetailComponent } from './item-detail/item-detail.component';

/**
 * Item routes.
 *
 * @author Manoel Veríssimo
 */
export const ItemRoutes: Routes = [
  {
    path: 'detail',
    component: ItemDetailComponent,
    canActivate: [
      SecurityGuard
    ]
  },
  {
    path: 'update',
    component: ItemFormComponent,
    data: {
      action: 'update'
    },
    canActivate: [
      SecurityGuard
    ]
  },
  {
    path: 'create',
    component: ItemFormComponent,
    data: {
      action: 'create'
    },
    canActivate: [
      SecurityGuard
    ]
  },
  {
    path: 'list',
    component: ItemListComponent,
    data: {
      action: 'list'  
    },
    canActivate: [
      SecurityGuard
    ]
  },
  {
    path: '',
    redirectTo: 'list',
    pathMatch: 'full',
    canActivate: [
      SecurityGuard
    ]
  }
];