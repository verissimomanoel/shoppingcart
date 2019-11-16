import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

import { SystemAction } from 'src/app/shared/ui/action';
import { MessageService } from 'src/app/shared/message/message.service';
import { CartClientService } from 'src/app/core/client/cart-client/cart-client.service';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ItemClientService } from 'src/app/core/client/item-client/item-client.service';
import { SecurityService } from 'src/app/core/security/security.service';

/**
 * Cart form component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'cart-form',
  templateUrl: './cart-form.component.html',
  styleUrls: ['./cart-form.component.scss']
})
export class CartFormComponent implements OnInit {
  public cart: any;
  public submitted: boolean;
  public action: SystemAction;
  public itemModal: BsModalRef;
  public items: Array<{}>;
  public totalItems: number = 0;

  /**
   * Constructor of class.
   * 
   * @param router 
   * @param orderPipe 
   * @param route 
   * @param modalService 
   * @param messageService 
   * @param cartClientService 
   */
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private modalService: BsModalService,
    private messageService: MessageService,
    public securityService: SecurityService,
    private cartClientService: CartClientService,
    private itemClientService: ItemClientService
  ) { }

  /**
   * Init component.
   */
  ngOnInit() {
    this.cart = this.route.snapshot.data['cart'];
    this.checkEmptyCart();
    this.initQtyItem();
  }

  /**
   * Check if a cart is empty.
   */
  private checkEmptyCart() {
    if (this.cart === undefined || this.cart.items === undefined || this.cart.items.length === 0) {
      this.messageService.addMsgWarning("MSG014");
    }
  }

  /**
   * Open modal mail.
   * 
   * @param template 
   */
  public onOpenItem(template: any): void {
    this.itemClientService.listAll().subscribe((data: any) => {
      this.items = data;
      this.itemModal = this.modalService.show(template, {
        backdrop: 'static',
        keyboard: false,
        animated: true,
        show: true
      });
    }, error => {
      this.messageService.addMsgDanger(error);
    });
  }

  /**
   * Add new item on cart.
   * 
   * @param item 
   */
  public onAddItem(item: any): void {
    if (item.value > 0) {
      let cartAddTO = {
        "userId": this.securityService.credential.user.id,
        "item": item
      };

      if (this.cart != undefined) {
        this.addItem(cartAddTO);
      } else {
        this.createCart(cartAddTO);
      }
    } else {
      this.messageService.addMsgDanger('MSG012')
    }
  }

  /**
   * Create a cart when the user doesn't have.
   * 
   * @param cartAddTO 
   */
  private createCart(cartAddTO: any) {
    let cart = {
      "user": {
        "id": this.securityService.credential.user.id,
        "name": this.securityService.credential.user.name,
        "email": this.securityService.credential.user.login,
      }
    }

    this.cartClientService.create(cart).subscribe(() => {
      this.messageService.addMsgInf("MSG011");
      this.addItem(cartAddTO);
    }, error => {
      this.messageService.addMsgDanger(error);
    });
  }

  /**
   * Add item on cart.
   * 
   * @param cartAddTO 
   */
  private addItem(cartAddTO: any) {
    this.cartClientService.addItem(cartAddTO).subscribe((data: any) => {
      this.cart = data;
      this.messageService.addMsgInf("MSG008");
      this.initQtyItem();
    }, error => {
      this.messageService.addMsgDanger(error);
    });
  }

  /**
   * Remove an item on cart.
   * 
   * @param item 
   */
  public onDelItem(item: any): void {
    this.messageService.addConfirmYesNo('MSG013', () => {
      let userId = this.securityService.credential.user.id;
      let itemId = item.id;

      this.cartClientService.delItem(userId, itemId).subscribe((data: any) => {
        this.cart = data;
        this.messageService.addMsgInf("MSG010");
        this.checkEmptyCart();
        this.initQtyItem();
      }, error => {
        this.messageService.addMsgDanger(error);
      });
    });
  }

  /**
   * Calculate the item quantity in cart.
   */
  private initQtyItem() {
    this.totalItems = 0;
    if (this.cart !== undefined) {
      this.cart.items.forEach(item => {
        this.totalItems = this.totalItems + item.quantity;
      });
    }
  }
}
