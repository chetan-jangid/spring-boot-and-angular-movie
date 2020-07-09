import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ApplicationStorageService } from '../services/application-storage.service';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthenticationGuard implements CanActivate {

  constructor(
    public applicationStorageService: ApplicationStorageService,
    public authenticationService: AuthenticationService,
    public router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if (this.authenticationService.isAdmin) {
        return true;
      } else {
        this.router.navigate(["/movie-multiplex-app/unauthorized-access"]);
      }
      return false;
  }
  
}
