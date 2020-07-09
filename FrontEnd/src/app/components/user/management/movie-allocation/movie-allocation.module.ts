import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MovieAllocationRoutingModule } from './movie-allocation-routing.module';
import { MovieAllocationComponent } from './movie-allocation.component';
import { AppUtilService } from 'src/app/services/app-util.service';
import { ApiService } from 'src/app/services/api.service';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NumbersOnlyModule } from 'src/app/directives/numbers-only/numbers-only.module';
import { ApplicationIconsModule } from 'src/app/utils/modules/application-icons.module';
import { ApplicationFormsModule } from 'src/app/utils/modules/app-form-module';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { StaticDataService } from 'src/app/services/static-data.service';

@NgModule({
  declarations: [MovieAllocationComponent],
  imports: [
    CommonModule,
    MovieAllocationRoutingModule,
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
export class MovieAllocationModule { }
