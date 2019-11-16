import { Component, Input } from '@angular/core';

/**
 * Enum type title.
 * 
 * @author Manoel Veríssimo
 */
export enum TypeTitle { title, subtitle }

/**
 * Page title component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'app-page-title',
  templateUrl: './pagetitle.component.html',
  styleUrls: ['./pagetitle.component.scss']
})
export class PagetitleComponent {

  @Input() icon: string;
  @Input() title: boolean;
  @Input() subtitle: boolean;
  @Input() description: string;

}
