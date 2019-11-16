import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

import { SystemAction } from 'src/app/shared/ui/action';
import { MessageService } from 'src/app/shared/message/message.service';
import { UserClientService } from 'src/app/core/client/user-client/user-client.service';

/**
 * User form component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'user-form',
  templateUrl: './user-form.component.html'
})
export class UserFormComponent implements OnInit {
  public user: any;
  public submitted: boolean;
  public action: SystemAction;

  /**
   * Constructor of class.
   * 
   * @param router 
   * @param orderPipe 
   * @param route 
   * @param modalService 
   * @param messageService 
   * @param userClientService 
   */
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,
    private userClientService: UserClientService
  ) {
    this.action = new SystemAction(this.route);
    if (this.action.isCreate()) {
      this.user = {};
    } else if (history.state.user != null) {
      this.user = history.state.user;
    } else {
      this.router.navigate(['user']);
    }
  }

  /**
   * Init component.
   */
  ngOnInit() {

  }

  /**
   * Create or update user.
   * 
   * @param user 
   * @param form 
   */
  public onSubmit(user: any, form: NgForm): void {
    this.submitted = true;

    if (form.invalid) {
      return;
    }

    if (user.id) {
      this.userClientService.update(user).subscribe(() => {
        this.router.navigate(['user']);
        this.messageService.addMsgInf("MSG006");
      }, error => {
        this.messageService.addMsgDanger(error);
      });
    } else {
      this.userClientService.create(user).subscribe(() => {
        this.router.navigate(['user']);
        this.messageService.addMsgInf("MSG005");
      }, error => {
        this.messageService.addMsgDanger(error);
      });
    }
  }
}
