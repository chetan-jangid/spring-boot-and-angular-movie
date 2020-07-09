import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UnauthorizedAccessComponent } from './unauthorized-access.component';
import { UnauthorizedAccessRoutingModule } from './unauthorized-access-routing.module';
import { NzResultModule } from 'ng-zorro-antd/result';
import { NzButtonModule } from 'ng-zorro-antd/button';

@NgModule({
  declarations: [UnauthorizedAccessComponent],
  imports: [
    CommonModule,
    UnauthorizedAccessRoutingModule,
    NzResultModule,
    NzButtonModule
  ]
})
export class UnauthorizedAccessModule { }
