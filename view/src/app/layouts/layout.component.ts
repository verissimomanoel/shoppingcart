import { Component, AfterViewInit } from '@angular/core';

/**
 * Layout component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements AfterViewInit {

  private landscape: boolean = false;

  /**
   * Checked browser mobile.
   */
  public isMobile(): boolean {
    const ua = navigator.userAgent;
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini|Mobile|mobile|CriOS/i.test(ua);
  }

  /**
   * Init component after view.
   */
  ngAfterViewInit() {
    document.body.classList.remove('authentication-bg');
    document.body.classList.remove('authentication-bg-pattern');

    if (this.isMobile()) {
      window.addEventListener('orientationchange', () => {
        this.landscape = window.orientation === 90 || window.orientation === -90;

        setTimeout(() => {
          document.body.classList.remove('enlarged');
          document.body.classList.remove('sidebar-enable');

          //Landscape - Enlarged Menu
          if (this.landscape) {
            document.body.classList.add('enlarged');
            document.body.classList.add('sidebar-enable');
          }
        });
      });
    } else {
      document.body.classList.add('sidebar-enable');
    }
  }

  /**
   * On mobile toggle button clicked
   */
  public onToggleMobileMenu(): void {
    document.body.classList.toggle('sidebar-enable');
    if (!this.isMobile() || this.landscape) {
      document.body.classList.toggle('enlarged');
    }
  }
}
