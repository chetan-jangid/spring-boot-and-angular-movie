import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MovieAllocationComponent } from './movie-allocation.component';

const routes: Routes = [
  { path: "", component: MovieAllocationComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MovieAllocationRoutingModule { }
