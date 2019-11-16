import { ActivatedRoute, ActivatedRouteSnapshot } from '@angular/router';

/**
 * Enum with the possible representations of action.
 */
export enum Action {
  LIST = 'list',
  CREATE = 'create',
  UPDATE = 'update',
  DETAIL = 'detail',
  ANALYZE = 'analyze',
}

/**
 * Action control class.
 *
 * @author Manoel Veríssimo
 */
export class SystemAction {

  private action: Action;

  /**
   * Constructor of class.
   *
   * @param route
   */
  constructor(route?: ActivatedRoute | ActivatedRouteSnapshot) {

    if (route instanceof ActivatedRoute) {
      this.action = route.snapshot.data.action;
    }

    if (route instanceof ActivatedRouteSnapshot) {
      this.action = route.data.action;
    }
  }

  /**
   * Sets the value of the current action.
   *
   * @param action
   */
  public setAction(action: Action): SystemAction {
    this.action = action;
    return this;
  }

  /**
   * Checks whether action is for 'CREATE'.
   *
   * @return boolean
   */
  public isCreate(): boolean {
    return Action.CREATE === this.action;
  };

  /**
   * Checks whether action is for 'UPDATE'.
   *
   * @return boolean
   */
  public isUpdate(): boolean {
    return Action.UPDATE === this.action;
  }

  /**
   * Checks whether action is for 'LIST'.
   *
   * @return boolean
   */
  public isList(): boolean {
    return Action.LIST === this.action;
  }

  /**
   * Checks whether action is for 'DETAIL'.
   *
   * @return boolean
   */
  public isDetail(): boolean {
    return Action.DETAIL === this.action;
  }

  /**
   * Checks whether action is for 'ANALYZE'.
   *
   * @return boolean
   */
  public isAnalyze(): boolean {
    return Action.ANALYZE === this.action;
  }
}