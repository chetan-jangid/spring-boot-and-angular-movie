import { Injectable } from '@angular/core';
import { ApplicationStorageService } from './application-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(public applicationStorageService: ApplicationStorageService) { }

  get isAdmin(): boolean {
    if (this.applicationStorageService.get(this.applicationStorageService.KEYS.AUTH_TOKEN)) {
      return true;
    }
    return false;
  }

}
