import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { MessageService } from 'src/app/shared/message/message.service';
import { SecurityService } from 'src/app/core/security/security.service';
import { UserClientService } from 'src/app/core/client/user-client/user-client.service';
import { NgForm } from '@angular/forms';

/**
 * User list component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'user-list',
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {

  public filterTO: any;
  public users: Array<{}>;
  public searched: boolean;
  public submitted: boolean;

  /**
   * Constructor of class.
   * 
   * @param route 
   * @param messageService 
   * @param securityService 
   * @param userClientService 
   */
  constructor(
    private route: ActivatedRoute,
    private messageService: MessageService,
    public securityService: SecurityService,
    private userClientService: UserClientService,
    private router: Router
  ) { }

  /**
   * Init component.
   */
  ngOnInit() {
    this.onCleanFilter();
  }

  /**
   * Clean filter.
   */
  public onCleanFilter(): void {
    delete this.users;
    this.filterTO = {};
    this.searched = false;
  }

  /**
   * Search user by filter.
   * 
   * @param filterTO 
   */
  public onSubmit(filterTO: any, form: NgForm): void {
    this.submitted = true;

    if (form.invalid) {
      return;
    }

    this.search(filterTO);
  }

  /**
   * Search user by filter.
   * 
   * @param filterTO 
   */
  private search(filterTO: any) {
    this.userClientService.search(filterTO).subscribe((data: any) => {
      this.users = data;
      this.searched = true;
    }, error => {
      delete this.users;
      if (error.status !== 400) {
        this.searched = false;
        this.messageService.addMsgDanger(error);
      }
      else {
        this.searched = true;
      }
    });
  }

  /**
   * Delete user by id.
   * 
   * @param user 
   */
  public onDelete(user: any): void {
    this.messageService.addConfirmYesNo('MSG002', () => {
      this.userClientService.delete(user.id).subscribe(() => {
        this.messageService.addMsgInf('MSG007');
        this.search(this.filterTO);
      }, error => {
        this.messageService.addMsgDanger(error);
      });
    });
  }

  /**
   * Return the id of user logged.
   */
  public getUserId() {
    return this.securityService.credential.user.id;
  }

}
