import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

/**
 * User detail component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'user-detail',
  templateUrl: './user-detail.component.html'
})
export class UserDetailComponent implements OnInit {

  public user: any;
  public address: any;

  /**
   * Constructor of class.
   * 
   * @param route 
   */
  constructor(
    private router: Router
  ) {
    if (history.state.user != null) {
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
}
