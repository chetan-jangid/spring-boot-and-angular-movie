import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationStorageService } from '../application-storage.service';

@Injectable({
  providedIn: 'root'
})
export class GlobalHttpInterceptorHandlerService implements HttpInterceptor {

  constructor(public applicationStorageService: ApplicationStorageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let request = req.clone({
      setHeaders: {
        "Authorization": "Bearer "
        + this.applicationStorageService.get(this.applicationStorageService.KEYS.AUTH_TOKEN)
      }
    });
    return next.handle(request);
  }
  
}
