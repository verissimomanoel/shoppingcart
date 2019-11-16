import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

import { SecurityService, config, IConfig } from './core/security/security.service';
import { AuthClientService } from './core/client/auth-client/auth-client.service';
import { MessageService, MessageItem } from './shared/message/message.service';
import { User } from './core/security/credential';

@Component({
  selector: 'app-adminto',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  public confirmModal: BsModalRef;

  public confirmItem: MessageItem;

  @ViewChild('confirm', { static: false }) confirmTemplate: any;

  /**
   * Constructor of class.
   * 
   * @param authClientService 
   * @param securityService 
   * @param config 
   * @param messageService 
   * @param modalService 
   * @param loaderService 
   * @param router 
   */
  constructor(
    private authClientService: AuthClientService,
    private securityService: SecurityService,
    @Inject(config) private config: IConfig,
    private messageService: MessageService,
    private modalService: BsModalService,
    private toastrService: ToastrService,
    private router: Router
  ) { }

  /**
   * Init listeners app.
   */
  ngOnInit(): void {
    this.securityService.onRefresh.subscribe((refreshToken: string) => {
      this.authClientService.refresh(refreshToken).subscribe((data: any) => {
        const user: User = {
          id: data.userId,
          name: data.name,
          login: data.login,
          roles: data.roles,
          accessToken: data.accessToken,
          expiresIn: data.accessExpiresIn,
          refreshToken: data.refreshToken
        }
        this.securityService.init(user);
      }, error => {
        this.messageService.addMsgDanger(error);
      });
    });

    this.securityService.onForbidden.subscribe(() => {
      this.router.navigate([this.config.rootRoute]);
    });

    this.securityService.onUnauthorized.subscribe(() => {
      this.router.navigate([this.config.loginRoute]);
      this.securityService.invalidate();
    });

    this.securityService.init();

    this.messageService.getConfirmEmitter().subscribe((confirmItem: MessageItem) => {
      this.confirmItem = confirmItem;
      this.confirmModal = this.modalService.show(this.confirmTemplate, {
        backdrop: 'static',
        keyboard: false,
        animated: true,
        show: true
      });
    });

    this.messageService.getMsgEmitter().subscribe((item: MessageItem) => {
      switch (item.type) {
        case MessageItem.ALERT_TYPE_INFO:
          this.toastrService.info(item.msg);
          break;
        case MessageItem.ALERT_TYPE_SUCCES:
          this.toastrService.success(item.msg);
          break;
        case MessageItem.ALERT_TYPE_WARNING:
          this.toastrService.warning(item.msg);
          break;
        case MessageItem.ALERT_TYPE_DANGER:
          this.toastrService.error(item.msg);
          break;
      }
    });
  }

  /**
  * Callback to actions 'YES/OK'.
  *
  * @param confirmItem
  */
  public onConfirmYesOk(confirmItem: MessageItem): void {
    this.confirmModal.hide();
    confirmItem.executYesOk();
    delete this.confirmItem;
  }

  /**
   * Callback to actions 'NO'.
   *
   * @param confirmItem
   */
  public onConfirmNo(confirmItem: MessageItem): void {
    this.confirmModal.hide();
    confirmItem.executNo();
    delete this.confirmItem;
  }
}
