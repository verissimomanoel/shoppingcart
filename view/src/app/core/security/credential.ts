import { IConfig } from './security.service';

/**
 * Interface with the representation of 'User'.
 * 
 * @author Manoel Veríssimo
 */
export interface User {
  id: string;
  name: string;
  login: string;
  roles?: string[];
  expiresIn: number;
  accessToken: string;
  refreshToken: string;
}

/**
 * Class 'Credential'.
 *
 * @author Manoel Veríssimo
 */
export class Credential {

  private _user: User;

  /**
   * Construtor da classe.
   * 
   * @param config 
   */
  constructor(private config: IConfig) { }

  /**
   * Init credential.
   * 
   * @param user 
   */
  public init(user?: User): void {
    this._user = user;

    if (this._user) {
      let data = JSON.stringify(this._user);
      localStorage.setItem(this.config.storage, btoa(data));
    } else {
      let data = localStorage.getItem(this.config.storage);

      if (data) {
        data = atob(data);
        this._user = JSON.parse(data);
      }
    }
  }

  /**
   * Clean _user's credential.
   */
  public clean(): void {
    this._user = undefined;
    localStorage.removeItem(this.config.storage);
  }

  /**
   * @returns user
   */
  public get user(): User {
    return this._user;
  }

  /**
   * @returns userName
   */
  public get username(): string {
    return this._user ? this._user.name : undefined;
  }

  /**
   * @returns login
   */
  public get login(): string {
    return this._user ? this._user.login : undefined;
  }

  /**
   * @returns accessToken
   */
  public get accessToken(): string {
    return this._user ? this._user.accessToken : undefined;
  }

  /**
   * @returns roles
   */
  public get roles(): string[] {
    return this._user ? this._user.roles : [];
  }

  /**
   * @returns refreshToken
   */
  public get refreshToken(): string {
    return this._user ? this._user.refreshToken : undefined;
  }
}