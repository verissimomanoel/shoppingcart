import { NgxMaskModule } from 'ngx-mask'
import { ToastrModule } from 'ngx-toastr';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgModule, LOCALE_ID } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppMessage } from './app.message';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LayoutsModule } from './layouts/layouts.module';
import { LoaderModule } from './shared/loader/loader.module';
import { MessageModule } from './shared/message/message.module';
import { AppInterceptor } from './core/helpers/app.interceptor';
import { SecurityModule } from './core/security/security.module';
import { SecurityInterceptor } from './core/security/security.interceptor';
import { MessageResourceProvider } from './shared/message/message.resource';
import { AuthClientModule } from './core/client/auth-client/auth-client.module';
import { UIModule } from './shared/ui/ui.module';

/**
 * 
 */
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    UIModule,
    LoaderModule,
    LayoutsModule,
    BrowserModule,
    FlexLayoutModule,
    HttpClientModule,
    AppRoutingModule,
    AuthClientModule,
    ModalModule.forRoot(),
    ToastrModule.forRoot({
      enableHtml: true,
      progressBar: true,
      closeButton: true,
      timeOut: 5000,
      preventDuplicates: true,
      progressAnimation: 'increasing',
    }),
    BrowserAnimationsModule,
    MessageModule.forRoot(),
    NgxMaskModule.forRoot({}),
    SecurityModule.forRoot({
      rootRoute: '/',
      loginRoute: '/account/login',
      passwordRoute: '/user/password',
      storage: 'ShoppingCartStorage'
    }),
  ],
  providers: [
    {
      provide: MessageResourceProvider,
      useValue: AppMessage,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AppInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: SecurityInterceptor,
      multi: true
    },
    {
      provide: LOCALE_ID,
      useValue: 'en',
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }