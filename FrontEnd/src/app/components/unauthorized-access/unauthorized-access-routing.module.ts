import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UnauthorizedAccessComponent } from './unauthorized-access.component';

const routes: Routes = [
  { path: "", component: UnauthorizedAccessComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UnauthorizedAccessRoutingModule { }
