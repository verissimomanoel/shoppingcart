import { RouterModule } from '@angular/router';
import { ModuleWithProviders, NgModule } from '@angular/core';

import { SecurityGuard } from './security.guard';
import { SecurityService, options, CONFIG, INITIAL, initial, config, _configFactory } from './security.service';

/**
 * Module responsible for encapsulating security mechanisms.
 * 
 * @author Manoel Veríssimo
 */
@NgModule({
  imports: [
    RouterModule
  ],
  providers: [
    SecurityGuard,
    SecurityService
  ],
  declarations: []
})
export class SecurityModule {

  public static forRoot(configValue?: options): ModuleWithProviders {
    return {
      ngModule: SecurityModule,
      providers: [
        {
          provide: CONFIG,
          useValue: configValue
        },
        {
          provide: INITIAL,
          useValue: initial
        },
        {
          provide: config,
          useFactory: _configFactory,
          deps: [INITIAL, CONFIG]
        }
      ]
    };
  }
}

