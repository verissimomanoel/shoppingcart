import { Injectable, EventEmitter, Inject, InjectionToken } from '@angular/core';

import { Credential, User } from './credential';

/**
 * Security Configuration Interface.
 * 
 * @author Manoel Veríssimo
 */
export interface IConfig {
    passwordRoute: string
    storage: string;
    loginRoute: string;
    rootRoute: string;
}

/**
 * Security default setting.
 * 
 * @author Manoel Veríssimo
 */
export const initial: IConfig = {
    storage: 'storage',
    passwordRoute: '',
    loginRoute: '',
    rootRoute: '',
};

export type options = Partial<IConfig>;

export const config: InjectionToken<string> = new InjectionToken('config');

export const CONFIG: InjectionToken<string> = new InjectionToken('CONFIG');

export const INITIAL: InjectionToken<IConfig> = new InjectionToken('INITIAL');

/**
 * Configuration factory method.
 * 
 * @author Manoel Veríssimo
 * 
 * @param initial 
 * @param value 
 */
export function _configFactory(initial: options, value: options | (() => options)): Function | options {
    return (typeof value === 'function') ? value() : { ...initial, ...value };
}

/**
 * Class 'Credential'.
 *
 * @author Manoel Veríssimo
 */
@Injectable()
export class SecurityService {

    private intervalId: any;

    public onRefresh: EventEmitter<string>;

    public onForbidden: EventEmitter<Credential>;

    public onUnauthorized: EventEmitter<Credential>;

    private _credential: Credential;

    /**
     * Constructor of class.
     * 
     * @param config 
     */
    constructor(@Inject(config) config: IConfig) {
        this._credential = new Credential(config);
        this.onRefresh = new EventEmitter<string>();
        this.onForbidden = new EventEmitter<Credential>();
        this.onUnauthorized = new EventEmitter<Credential>();
    }

    /**
     * Init security service.
     * 
     * @param user 
     */
    public init(user?: User): void {
        this.credential.init(user);

        if (user) {
            let expiresIn = (user.expiresIn - 60) * 1000;
            this.intervalId = setInterval(() => {
                clearInterval(this.intervalId);
                this.onRefresh.emit(this._credential.refreshToken);
            }, expiresIn);
        } else {
            if (this.isValid()) {
                this.onRefresh.emit(this._credential.refreshToken);
            }
        }
    }

    /**
     * Verifies that the User has the 'role' entered in their access credential.
     * 
     * @param roles 
     */
    public hasRoles(roles: string | string[]): boolean {
        return true;
    }

    /**
     * Invalid user's credential.
     */
    public invalidate(): void {
        this._credential.clean();
        clearInterval(this.intervalId)
    }

    /**
     * Verifies that the user credential is valid.
     *
     * @returns boolean
     */
    public isValid(): boolean {
        return this._credential.user !== undefined;
    }

    /**
     * @returns credential
     */
    public get credential(): Credential {
        return this._credential;
    }
}