import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MovieRoutingModule } from './movie-routing.module';
import { MovieComponent } from '../movie/movie.component';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { ApplicationFormsModule } from 'src/app/utils/modules/app-form-module';
import { ApiService } from 'src/app/services/api.service';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NumbersOnlyModule } from 'src/app/directives/numbers-only/numbers-only.module';
import { AppUtilService } from 'src/app/services/app-util.service';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { StaticDataService } from 'src/app/services/static-data.service';

@NgModule({
  declarations: [MovieComponent],
  imports: [
    CommonModule,
    MovieRoutingModule,
    NzLayoutModule,
    NzRadioModule,
    NzButtonModule,
    ApplicationFormsModule,
    NumbersOnlyModule,
    NzModalModule
  ],
  providers: [ApiService, AppUtilService, StaticDataService]
})
export class MovieModule { }
