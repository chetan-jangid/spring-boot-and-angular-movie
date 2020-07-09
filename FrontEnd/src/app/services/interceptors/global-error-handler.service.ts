import { Injectable, ErrorHandler } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { AppUtilService } from '../app-util.service';

@Injectable({
  providedIn: 'root'
})
export class GlobalErrorHandlerService implements ErrorHandler {

  constructor(public appUtilService: AppUtilService) { }

  handleError(error: any): void {
    console.error(error);
    if (error instanceof HttpErrorResponse) {
      //handle http errors
      console.log(error);
      if (error.error && error.error.errorMessage) {
        alert(error.error.errorMessage);
        // this.appUtilService.dialog({ type: "error", title: error.error.errorMessage });
      }
    }
  }

}
