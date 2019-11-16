import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './auth.guard';
import { LoginComponent } from './login/login.component';

/**
 * Auth router.
 * 
 * @author Manoel Veríssimo
 */
const routes: Routes = [
    {
        path: 'login',
        component: LoginComponent,
        canActivate: [
            AuthGuard
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AuthRoutingModule { }
