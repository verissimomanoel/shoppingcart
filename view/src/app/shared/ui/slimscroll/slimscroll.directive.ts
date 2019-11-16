import { Directive, ElementRef, AfterViewInit } from '@angular/core';
import Scrollbar from 'smooth-scrollbar';

@Directive({
  selector: '[slimScroll]'
})
export class SlimscrollDirective implements AfterViewInit {

  constructor(
    private el: ElementRef
  ) { }

  ngAfterViewInit() {
    Scrollbar.init(this.el.nativeElement);
  }

}
