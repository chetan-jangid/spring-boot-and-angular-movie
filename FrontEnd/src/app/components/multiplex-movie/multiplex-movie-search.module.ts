import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NzAutocompleteModule } from 'ng-zorro-antd/auto-complete';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { MultiplexMovieSearchRoutingModule } from './multiplex-movie-search-routing.module';

import { ApplicationFormsModule } from 'src/app/utils/modules/app-form-module';
import { ApplicationIconsModule } from 'src/app/utils/modules/application-icons.module';
import { ApiService } from 'src/app/services/api.service';
import { StaticDataService } from 'src/app/services/static-data.service';
import { AppUtilService } from 'src/app/services/app-util.service';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { MultiplexMovieSearchComponent } from './multiplex-movie-search.component';

@NgModule({
  declarations: [
    MultiplexMovieSearchComponent
  ],
  imports: [
    MultiplexMovieSearchRoutingModule,
    CommonModule,
    NzAutocompleteModule,
    NzLayoutModule,
    NzRadioModule,
    NzButtonModule,
    ApplicationFormsModule,
    ApplicationIconsModule,
    NzModalModule,
    NzButtonModule
  ],
  providers: [ApiService, AppUtilService, StaticDataService]
})
export class MultiplexMovieSearchModule { }
