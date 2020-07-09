import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MultiplexComponent } from './multiplex.component';

const routes: Routes = [
  { path: "", component: MultiplexComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MultiplexRoutingModule { }
