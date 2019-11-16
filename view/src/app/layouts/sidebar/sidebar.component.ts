import { Component, AfterViewInit, ElementRef, ViewChild, OnChanges } from '@angular/core';
import MetisMenu from 'metismenujs/dist/metismenujs';
import { SecurityService } from 'src/app/core/security/security.service';

/**
 * Sidebar component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements AfterViewInit, OnChanges {

  private menu: any;

  @ViewChild('sideMenu', { static: false }) sideMenu: ElementRef;

  /**
   * Constructor of class.
   * 
   * @param securityService 
   */
  constructor(public securityService: SecurityService) { }

  /**
   * Init component after view.
   */
  ngAfterViewInit() {
    this.menu = new MetisMenu(this.sideMenu.nativeElement);
  }

  /**
   * Change listener.
   */
  ngOnChanges() {
    this.menu.dispose();
    this.menu = new MetisMenu(this.sideMenu.nativeElement);
  }
}
