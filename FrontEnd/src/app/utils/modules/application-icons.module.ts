import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import * as allIcons from '@ant-design/icons-angular/icons';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { IconDefinition } from '@ant-design/icons-angular';

const antIcons = allIcons as {
  [key: string]: IconDefinition
};

const icons: IconDefinition[] = Object.keys(antIcons).map(key => antIcons[key]);

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    NzIconModule.forRoot(icons)
  ],
  exports: [
    NzIconModule
  ]
})
export class ApplicationIconsModule { }
