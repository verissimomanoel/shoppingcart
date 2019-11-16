import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';

import { SecurityService } from './security.service';

/**
 * Implementation responsible for intercepting Http requests.
 * 
 * @author Manoel Veríssimo
 */
@Injectable()
export class SecurityInterceptor implements HttpInterceptor {

    /**
     * Constructor of class.
     * 
     * @param securityService 
     */
    constructor(private securityService: SecurityService) { }

    /**
     * Method responsible for intercepting the Http request.
     * 
     * @param request 
     * @param next 
     */
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.securityService.isValid()) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${this.securityService.credential.accessToken}`
                }
            });
        }

        return next.handle(request).pipe(catchError((response: HttpErrorResponse): Observable<HttpEvent<any>> => {

            if (response.status === 401) {
                this.securityService.onUnauthorized.emit(this.securityService.credential);
            }

            if (response.status === 403) {
                this.securityService.onForbidden.emit(this.securityService.credential);
            }
            return throwError(response);
        }));
    }
}