import { Injectable, EventEmitter } from "@angular/core";
import { InternationalizationPipe } from './internationalization.pipe';

/**
 * Representation class of 'Message'.
 * 
 * @author Manoel Veríssimo
 */
export class Message {

  /**
   * Constructor of class.
   * 
   * @param code 
   * @param error 
   * @param message 
   * @param status 
   */
  constructor(
    public code?: string,
    public error?: string,
    public message?: string,
    public status?: number
  ) { }
}

/**
 * Message item class.
 *
 * @author Manoel Veríssimo
 */
export class MessageItem {

  public static ALERT_TYPE_INFO = "info";
  public static ALERT_TYPE_DANGER = "danger";
  public static ALERT_TYPE_SUCCES = "success";
  public static ALERT_TYPE_WARNING = "warning";

  public static CONFIRM_TYPE_OK = "confirm_ok";
  public static CONFIRM_TYPE_YES_NO = "confirm_yes_no";

  private _msg: string;
  private _type: string;
  private _listenerNo: ConfirmListener;
  private _listenerYesOk: ConfirmListener;

  /**
   * Constructor of class.
   *
   * @param msg
   * @param type
   * @param listenerYesOk
   * @param listenerNo
   */
  constructor(
    msg: string,
    type: string,
    listenerYesOk?: ConfirmListener,
    listenerNo?: ConfirmListener
  ) {
    this._msg = msg;
    this._type = type;
    this._listenerNo = listenerNo;
    this._listenerYesOk = listenerYesOk;
  }

  /**
   * @returns msg
   */
  public get msg(): string {
    return this._msg;
  }

  /**
   * @returns type
   */
  public get type(): string {
    return this._type;
  }

  /**
   * Callbacks 'OK/YES' actions.
   */
  public executYesOk(): void {
    if (this._listenerYesOk !== null && this._listenerYesOk !== undefined) {
      this._listenerYesOk();
    }
  }

  /**
   * Callback 'NO' actions.
   */
  public executNo(): void {
    if (this._listenerNo !== null && this._listenerNo !== undefined) {
      this._listenerNo();
    }
  }

  /**
   * Checks if item has 'type' equals 'CONFIRM_TYPE_OK'.
   *
   * @returns boolean
   */
  public isConfirmTypeOk(): boolean {
    return MessageItem.CONFIRM_TYPE_OK === this.type;
  }

  /**
   * Checks if item has 'type' equals 'CONFIRM_TYPE_YES_NO'.
   *
   * @returns boolean
   */
  public isConfirmTypeYesNo(): boolean {
    return MessageItem.CONFIRM_TYPE_YES_NO === this.type;
  }
}

/**
 * Listener interface that determines the callback function contract for confirm.
 *
 * @author Manoel Veríssimo
 */
export interface ConfirmListener {
  (): void;
}

/**
 * Service class responsible for providing the application messaging feature.
 *
 * @author Manoel Veríssimo
 */
@Injectable()
export class MessageService {

  private i18nPipe: InternationalizationPipe;
  private msgEmitter: EventEmitter<MessageItem>;
  private confirmEmitter: EventEmitter<MessageItem>;

  /**
   * Construtor da classe.
   *
   * @param i18nPipe
   */
  constructor(i18nPipe: InternationalizationPipe) {
    this.i18nPipe = i18nPipe;
    this.msgEmitter = new EventEmitter();
    this.confirmEmitter = new EventEmitter();
  }

  /**
   * Returns the message description according to the given parameters.
   *
   * @param msg
   */
  public getDescription(msg: string, params?: any): string {
    let description = null;

    if (msg !== null && msg !== undefined) {
      description = this.i18nPipe.transform(msg, params);
      description = description === undefined ? msg : description;
    }
    return description;
  }

  /**
   * Adds the confirmation modal according to type (confirm_ok, confirm_yes_no), entered.
   *
   * @param msg
   * @param type
   * @param params
   */
  private addConfirm(
    msg: string,
    type: string,
    params: any,
    listenerYesOk: ConfirmListener,
    listenerNo?: ConfirmListener
  ): void {
    let description = this.getDescription(msg, params);

    if (description) {
      this.confirmEmitter.emit(
        new MessageItem(description, type, listenerYesOk, listenerNo)
      );
    }
  }

  /**
   * Adds the message according to the type (alert-success, alert-info, alert-warning and alert-danger), entered.
   *
   * @param msg
   * @param type
   * @param params
   */
  private addMsg(data: Message | string, type: string, params?: any): void {
    let msg = data instanceof Message ? data.message : data;
    let description = this.getDescription(msg, params);

    if (description) {
      this.msgEmitter.emit(new MessageItem(description, type));
    }
  }

  /**
   * Adds confirm OK.
   *
   * @param msg
   * @param listenerOk
   * @param params
   */
  public addConfirmOk(
    msg: string,
    listenerOk?: ConfirmListener,
    params?: any
  ): void {
    this.addConfirm(msg, MessageItem.CONFIRM_TYPE_OK, params, listenerOk);
  }

  /**
   * Adds confirm YES/NO.
   *
   * @param msg
   * @param listenerYes
   * @param listenerNo
   * @param params
   */
  public addConfirmYesNo(
    msg: string,
    listenerYes?: ConfirmListener,
    listenerNo?: ConfirmListener,
    params?: any
  ): void {
    this.addConfirm(
      msg,
      MessageItem.CONFIRM_TYPE_YES_NO,
      params,
      listenerYes,
      listenerNo
    );
  }

  /**
   * Adds success message.
   *
   * @param msg
   * @param params
   */
  public addMsgSuccess(msg: Message | string, params?: any): void {
    this.addMsg(msg, MessageItem.ALERT_TYPE_SUCCES, params);
  }

  /**
   * Add info message.
   *
   * @param msg
   * @param params
   */
  public addMsgInf(msg: Message | string, params?: any): void {
    this.addMsg(msg, MessageItem.ALERT_TYPE_INFO, params);
  }

  /**
   * Adds alert message.
   *
   * @param msg
   * @param params
   */
  public addMsgWarning(msg: Message | string, params?: any): void {
    this.addMsg(msg, MessageItem.ALERT_TYPE_WARNING, params);
  }

  /**
   * Adds error message.
   *
   * @param msg
   * @param params
   */
  public addMsgDanger(msg: Message | string, params?: any): void {
    this.addMsg(msg, MessageItem.ALERT_TYPE_DANGER, params);
  }

  /**
   * @returns EventEmitter
   */
  public getMsgEmitter(): EventEmitter<MessageItem> {
    return this.msgEmitter;
  }

  /**
   * @returns EventEmitter
   */
  public getConfirmEmitter(): EventEmitter<MessageItem> {
    return this.confirmEmitter;
  }
}
