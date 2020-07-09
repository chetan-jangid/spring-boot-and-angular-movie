import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MultiplexRoutingModule } from './multiplex-routing.module';
import { MultiplexComponent } from '../multiplex/multiplex.component';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { ApplicationFormsModule } from 'src/app/utils/modules/app-form-module';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NumbersOnlyModule } from 'src/app/directives/numbers-only/numbers-only.module';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { ApiService } from 'src/app/services/api.service';
import { AppUtilService } from 'src/app/services/app-util.service';
import { StaticDataService } from 'src/app/services/static-data.service';
import { ApplicationIconsModule } from 'src/app/utils/modules/application-icons.module';

@NgModule({
  declarations: [MultiplexComponent],
  imports: [
    CommonModule,
    MultiplexRoutingModule,
    NzLayoutModule,
    NzRadioModule,
    NzButtonModule,
    ApplicationFormsModule,
    ApplicationIconsModule,
    NumbersOnlyModule,
    NzModalModule
  ],
  providers: [ApiService, AppUtilService, StaticDataService]
})
export class MultiplexModule { }
