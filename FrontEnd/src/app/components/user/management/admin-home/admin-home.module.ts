import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminHomeRoutingModule } from './admin-home-routing.module';
import { AdminHomeComponent } from './admin-home.component';
import { AdminSidebarComponent } from '../admin-sidebar/admin-sidebar.component';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzTypographyModule } from 'ng-zorro-antd/typography';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { ApplicationIconsModule } from 'src/app/utils/modules/application-icons.module';
import { AdminHeaderComponent } from 'src/app/components/user/management/admin-header/admin-header.component';

@NgModule({
  declarations: [
    AdminHomeComponent,
    AdminSidebarComponent,
    AdminHeaderComponent
  ],
  imports: [
    CommonModule,
    AdminHomeRoutingModule,
    NzLayoutModule,
    NzMenuModule,
    NzTypographyModule,
    NzRadioModule,
    ApplicationIconsModule
  ]
})
export class AdminHomeModule { }
