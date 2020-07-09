import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { NZ_I18N, en_US } from 'ng-zorro-antd/i18n';
import { RenderViewComponent } from './components/render-view/render-view.component';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { HeaderComponent } from './components/header/header.component';
import { NzTypographyModule } from 'ng-zorro-antd/typography';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { GlobalHttpInterceptorHandlerService } from 'src/app/services/interceptors/global-http-interceptor-handler.service';
import { GlobalErrorHandlerService } from 'src/app/services/interceptors/global-error-handler.service';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { AppUtilService } from './services/app-util.service';

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    RenderViewComponent,
    HeaderComponent
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    NzLayoutModule,
    NzMenuModule,
    NzTypographyModule,
    HttpClientModule,
    NzModalModule
  ],
  providers: [
    { provide: NZ_I18N, useValue: en_US },
    { provide: HTTP_INTERCEPTORS, useClass: GlobalHttpInterceptorHandlerService, multi: true },
    { provide: ErrorHandler, useClass: GlobalErrorHandlerService },
    AppUtilService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
