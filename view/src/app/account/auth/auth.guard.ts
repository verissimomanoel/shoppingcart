import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SecurityService } from 'src/app/core/security/security.service';

/**
 * Auth Guard.
 * 
 * @author Manoel Veríssimo
 */
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  /**
   * Constructor of class.
   * 
   * @param router
   * @param securityService 
   */
  constructor(
    private router: Router,
    private securityService: SecurityService
  ) { }

  /**
   * Page access only invalid user credential.
   * 
   * @param next 
   * @param state 
   */
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let valid: boolean = true;

    if (this.securityService.isValid()) {
      this.router.navigate(['/']);
      valid = false;
    }
    return valid
  }

}
