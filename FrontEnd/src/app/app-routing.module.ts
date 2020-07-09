import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminAuthenticationGuard } from './guards/admin-authentication.guard';
import { RenderViewComponent } from './components/render-view/render-view.component';
import { AdminLoginGuard } from './guards/admin-login.guard';

const routes: Routes = [
  { path: "", redirectTo: "movie-multiplex-app/app/movie-search", pathMatch: "full" },
  {
    path: "movie-multiplex-app/app",
    component: RenderViewComponent,
    children: [
      {
        path: "movie-search",
        loadChildren: () => import("src/app/components/multiplex-movie/multiplex-movie-search.module")
        .then(m => m.MultiplexMovieSearchModule)
      }
    ]
  },
  {
    path: "movie-multiplex-app/management-user",
    canActivate: [AdminLoginGuard],
    loadChildren: () => import("src/app/components/user/management/management-login/management-login.module")
    .then(m => m.ManagementLoginModule),
  },
  {
    path: "movie-multiplex-app/management-user/home",
    canActivate: [AdminAuthenticationGuard],
    loadChildren: () => import("src/app/components/user/management/admin-home/admin-home.module")
    .then(m => m.AdminHomeModule)
  },
  {
    path: "movie-multiplex-app/unauthorized-access",
    loadChildren: () => import("src/app/components/unauthorized-access/unauthorized-access.module")
    .then(m => m.UnauthorizedAccessModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
