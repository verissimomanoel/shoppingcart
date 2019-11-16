import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

/**
 * Item detail component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'item-detail',
  templateUrl: './item-detail.component.html'
})
export class ItemDetailComponent implements OnInit {

  public item: any;
  public address: any;

  /**
   * Constructor of class.
   * 
   * @param route 
   */
  constructor(
    private router: Router
  ) {
    if (history.state.item != null) {
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
}
