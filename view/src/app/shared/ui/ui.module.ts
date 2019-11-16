import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AlphaPipe } from './alpha/alpha.pipe';
import { PagetitleComponent } from './pagetitle/pagetitle.component';
import { SlimscrollDirective } from './slimscroll/slimscroll.directive';

@NgModule({
  declarations: [
    PagetitleComponent,
    SlimscrollDirective,
    AlphaPipe
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    PagetitleComponent,
    SlimscrollDirective,
    AlphaPipe
  ]
})
export class UIModule { }
