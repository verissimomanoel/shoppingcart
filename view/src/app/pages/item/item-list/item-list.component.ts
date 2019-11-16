import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { MessageService } from 'src/app/shared/message/message.service';
import { SecurityService } from 'src/app/core/security/security.service';
import { ItemClientService } from 'src/app/core/client/item-client/item-client.service';
import { NgForm } from '@angular/forms';

/**
 * Item list component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'item-list',
  templateUrl: './item-list.component.html'
})
export class ItemListComponent implements OnInit {

  public filterTO: any;
  public items: Array<{}>;
  public searched: boolean;
  public submitted: boolean;

  /**
   * Constructor of class.
   * 
   * @param route 
   * @param messageService 
   * @param securityService 
   * @param itemClientService 
   */
  constructor(
    private route: ActivatedRoute,
    private messageService: MessageService,
    public securityService: SecurityService,
    private itemClientService: ItemClientService,
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
    delete this.items;
    this.filterTO = {};
    this.searched = false;
  }

  /**
   * Search item by filter.
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
   * Search item by filter.
   * 
   * @param filterTO 
   */
  private search(filterTO: any) {
    this.itemClientService.search(filterTO).subscribe((data: any) => {
      this.items = data;
      this.searched = true;
    }, error => {
      delete this.items;
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
   * Delete item by id.
   * 
   * @param item 
   */
  public onDelete(item: any): void {
    this.messageService.addConfirmYesNo('MSG002', () => {
      this.itemClientService.delete(item.id).subscribe(() => {
        this.messageService.addMsgInf('MSG010');
        this.search(this.filterTO);
      }, error => {
        this.messageService.addMsgDanger(error);
      });
    });
  }
}
