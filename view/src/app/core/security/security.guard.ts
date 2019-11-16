import { Observable } from 'rxjs';
import { Injectable, Inject } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { SecurityService, config, IConfig } from './security.service';

/**
 * Implementation that ensures the safety of routes allowing access only if the 'User' 
 * is authenticated in the application and has the necessary roles to access it.
 *
 * @author Manoel Veríssimo
 */
@Injectable({ providedIn: 'root' })
export class SecurityGuard implements CanActivate {

    /**
     * Constructor of class.
     *
     * @param router
     * @param securityService
     * @param config
     */
    constructor(
        private router: Router,
        private securityService: SecurityService,
        @Inject(config) private config: IConfig
    ) { }

    /**
     * It overrides the route and checks whether or not it can be displayed.
     *
     * @param next
     * @param state
     */
    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        let valid = false;

        if (this.securityService.isValid()) {
            let roles = next.data['security'] ? next.data['security'].roles : [];

            if (this.securityService.hasRoles(roles)) {
                valid = true;
            } else {
                this.router.navigate([this.config.rootRoute]);
            }
        } else {
            this.router.navigate([this.config.loginRoute]);
        }
        return valid;
    }

}
