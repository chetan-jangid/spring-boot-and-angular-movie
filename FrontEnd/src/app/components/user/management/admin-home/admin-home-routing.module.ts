import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminHomeComponent } from './admin-home.component';
import { AdminAuthenticationGuard } from 'src/app/guards/admin-authentication.guard';

const routes: Routes = [
  {
    path: "",
    component: AdminHomeComponent,
    children: [
      {
        path: "movie",
        canActivate: [AdminAuthenticationGuard],
        loadChildren: () => import("./../movie/movie.module")
        .then(m => m.MovieModule)
      },
      {
        path: "multiplex",
        canActivate: [AdminAuthenticationGuard],
        loadChildren: () => import("./../multiplex/multiplex.module")
        .then(m => m.MultiplexModule)
      },
      {
        path: "movie-allocation",
        canActivate: [AdminAuthenticationGuard],
        loadChildren: () => import("./../movie-allocation/movie-allocation.module")
        .then(m => m.MovieAllocationModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminHomeRoutingModule { }
