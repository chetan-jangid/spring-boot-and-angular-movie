import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ApiService } from 'src/app/services/api.service';
import { Router } from '@angular/router';
import { AppUtilService } from 'src/app/services/app-util.service';
import { ApplicationStorageService } from 'src/app/services/application-storage.service';

@Component({
  selector: 'app-management-login',
  templateUrl: './management-login.component.html',
  styleUrls: ['./management-login.component.scss']
})
export class ManagementLoginComponent implements OnInit {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    public apiService: ApiService,
    public router: Router,
    public appUtilService: AppUtilService,
    public applicationStorageService: ApplicationStorageService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });
  }

  submitForm(): void {
    this.appUtilService.checkFormValidations(this.form);
    if (this.form.valid) {
      this.apiService.post({ url: "/api-gateway/login", postBody: this.form.value }).subscribe(response => {
        this.applicationStorageService.set(this.applicationStorageService.KEYS.AUTH_TOKEN, response.token);
        this.router.navigate(["/movie-multiplex-app/management-user/home"]);
      }, error => this.appUtilService.dialog({ type: "error", title: error.error.errorMessage })
      );
    }
  }

}
