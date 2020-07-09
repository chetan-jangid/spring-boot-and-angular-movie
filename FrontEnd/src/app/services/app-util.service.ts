import { Injectable } from '@angular/core';
import { FormGroup, FormControl, FormArray } from '@angular/forms';
import { NzModalService, ModalOptions } from 'ng-zorro-antd/modal';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class AppUtilService {

  constructor(private modalService: NzModalService) { }

  checkFormValidations(formGroup: FormGroup) { 
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsDirty();
        control.updateValueAndValidity();
      } else if (control instanceof FormGroup) {
        this.checkFormValidations(control);
      } else if (control instanceof FormArray) {
        for (let i = 0; i < (control as FormArray).length; i++) {
          this.checkFormValidations((control as FormArray).at(i) as FormGroup);
        }
      }
    });
  }

  dialog(params: DialogParams): void {
    let options: ModalOptions = {
      nzTitle: params.title,
      nzContent: params.content
    };
    if (params.type == "success") {
      this.modalService.success(options);
    } else if (params.type == "error") {
      this.modalService.error(options);
    } else if (params.type == "warning") {
      this.modalService.warning(options);
    } else if (params.type == "info") {
      this.modalService.info(options);
    }
  }

  confirm(params: ConfirmParams): void {
    this.modalService.confirm({
      nzTitle: params.title,
      nzContent: params.content,
      nzOkText: params.okText ? params.okText : "Yes",
      nzOkType: 'danger',
      nzCancelText: params.cancelText ? params.cancelText : "No",
      nzOnOk: params.ok,
      nzOnCancel: params.cancel ? params.cancel : () => {}
    });
  }

  toDate(date: string, format: string): Date {
    return moment(date, format).toDate();
  }

  getYear(date: Date): number {
    return date.getFullYear();
  }

}

export interface DialogParams {
  type: string;
  title: string;
  content?: string;
}

export interface ConfirmParams {
  title: string;
  content?: string;
  okText?: string;
  ok: () => any,
  cancelText?: string;
  cancel?: () => any;
}
