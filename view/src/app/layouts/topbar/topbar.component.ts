import { Router } from '@angular/router';
import { Component, OnInit, Output, EventEmitter, Inject } from '@angular/core';
import { MessageService } from 'src/app/shared/message/message.service';
import { SecurityService, IConfig, config } from 'src/app/core/security/security.service';

/**
 * Topbar component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent implements OnInit {

  public pageHeading: string;
  public openMobileMenu: boolean;

  @Output() mobileMenuButtonClicked = new EventEmitter();

  /**
   * Constructor of class.
   * 
   * @param router 
   * @param messageService 
   * @param config 
   * @param securityService 
   */
  constructor(
    private router: Router,
    private messageService: MessageService,
    @Inject(config) private config: IConfig,
    private securityService: SecurityService
  ) { }

  /**
   * Init component.
   */
  ngOnInit() {
    this.openMobileMenu = false;
  }

  /**
   * Toggle the menu bar when having mobile screen
   * 
   * @param event
   */
  public toggleMobileMenu(event: any): void {
    event.preventDefault();
    this.mobileMenuButtonClicked.emit();
  }

  /**
   * Logout the user
   */
  logout(): void {
    this.messageService.addConfirmYesNo('MSG001', () => {
      this.securityService.invalidate();
      this.router.navigate([this.config.loginRoute]);
    });
  }

  public get username(): string {
    return this.securityService.credential.username;
  }
}
