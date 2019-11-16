import { Routes } from '@angular/router';

import { UserFormComponent } from './user-form/user-form.component';
import { UserListComponent } from './user-list/user-list.component';
import { SecurityGuard } from 'src/app/core/security/security.guard';
import { UserDetailComponent } from './user-detail/user-detail.component';

/**
 * User routes.
 *
 * @author Manoel Veríssimo
 */
export const UserRoutes: Routes = [
  {
    path: 'detail',
    component: UserDetailComponent,
    canActivate: [
      SecurityGuard
    ]
  },
  {
    path: 'update',
    component: UserFormComponent,
    data: {
      action: 'update'
    },
    canActivate: [
      SecurityGuard
    ]
  },
  {
    path: 'create',
    component: UserFormComponent,
    data: {
      action: 'create'
    },
    canActivate: [
      SecurityGuard
    ]
  },
  {
    path: 'list',
    component: UserListComponent,
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