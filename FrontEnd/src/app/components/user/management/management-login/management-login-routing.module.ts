import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ManagementLoginComponent } from './management-login.component';

const routes: Routes = [
  { path: "", component: ManagementLoginComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManagementLoginRoutingModule { }
