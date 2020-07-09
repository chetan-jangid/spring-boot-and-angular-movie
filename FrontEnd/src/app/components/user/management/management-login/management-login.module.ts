import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManagementLoginRoutingModule } from './management-login-routing.module';
import { ManagementLoginComponent } from './management-login.component';
import { ApplicationFormsModule } from 'src/app/utils/modules/app-form-module';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzTypographyModule } from 'ng-zorro-antd/typography';
import { ApplicationIconsModule } from 'src/app/utils/modules/application-icons.module';
import { ApiService } from 'src/app/services/api.service';
import { AppUtilService } from 'src/app/services/app-util.service';
import { ApplicationStorageService } from 'src/app/services/application-storage.service';
import { NzModalModule } from 'ng-zorro-antd/modal';

@NgModule({
  declarations: [ ManagementLoginComponent ],
  imports: [
    CommonModule,
    ManagementLoginRoutingModule,
    ApplicationFormsModule,
    NzButtonModule,
    NzLayoutModule,
    NzTypographyModule,
    ApplicationIconsModule,
    NzModalModule
  ],
  providers: [ ApiService, AppUtilService, ApplicationStorageService ]
})
export class ManagementLoginModule { }
