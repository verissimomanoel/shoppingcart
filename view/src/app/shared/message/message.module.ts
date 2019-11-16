import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModuleWithProviders } from '@angular/core';

import { MessageService } from './message.service';
import { InternationalizationPipe } from './internationalization.pipe';

/**
 * Module responsible for providing 'messaging' and 'i18n' features.
 *
 * @author Manoel Veríssimo
 */
@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    InternationalizationPipe
  ],
  exports: [
    InternationalizationPipe
  ]
})
export class MessageModule {

  /**
   * Convention used for the app module to make providers instances available as singleton for all application modules.
   */
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: MessageModule,
      providers: [
        MessageService,
        InternationalizationPipe
      ]
    }
  }
}
