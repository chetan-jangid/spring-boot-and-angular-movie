import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MultiplexMovieSearchComponent } from './multiplex-movie-search.component';

const routes: Routes = [
  {
    path: "",
    component: MultiplexMovieSearchComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MultiplexMovieSearchRoutingModule { }
