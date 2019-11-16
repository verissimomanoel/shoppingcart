import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

import { SystemAction } from 'src/app/shared/ui/action';
import { MessageService } from 'src/app/shared/message/message.service';
import { ItemClientService } from 'src/app/core/client/item-client/item-client.service';

/**
 * Item form component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'item-form',
  templateUrl: './item-form.component.html'
})
export class ItemFormComponent implements OnInit {
  public item: any;
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
   * @param itemClientService 
   */
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,
    private itemClientService: ItemClientService
  ) {
    this.action = new SystemAction(this.route);
    if (this.action.isCreate()) {
      this.item = {};
    } else if (history.state.item != null) {
      this.item = history.state.item;
    } else {
      this.router.navigate(['item']);
    }
  }

  /**
   * Init component.
   */
  ngOnInit() {

  }

  /**
   * Create or update item.
   * 
   * @param item 
   * @param form 
   */
  public onSubmit(item: any, form: NgForm): void {
    this.submitted = true;

    if (form.invalid) {
      return;
    }

    if (item.id) {
      this.itemClientService.update(item).subscribe(() => {
        this.router.navigate(['item']);
        this.messageService.addMsgInf("MSG009");
      }, error => {
        this.messageService.addMsgDanger(error);
      });
    } else {
      this.itemClientService.create(item).subscribe(() => {
        this.router.navigate(['item']);
        this.messageService.addMsgInf("MSG008");
      }, error => {
        this.messageService.addMsgDanger(error);
      });
    }
  }
}
