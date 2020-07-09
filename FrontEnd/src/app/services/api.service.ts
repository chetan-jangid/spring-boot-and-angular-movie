import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private httpClient: HttpClient
  ) { }

  get(params: ApiParams): Observable<any> {
    if (!params.options) {
      params.options = {};
    }
    return this.httpClient.get(environment.apiUrl + params.url, params.options);
  }

  post(params: ApiParams): Observable<any> {
    if (!params.options) {
      params.options = {};
    }
    return this.httpClient.post(environment.apiUrl + params.url, params.postBody, params.options);
  }
}

export interface ApiParams {
  url: string;
  postBody?: any;
  options?: {
    headers?: HttpHeaders | { [header: string]: string | string[]; };
    observe?: "body"; params?: HttpParams | { [param: string]: string | string[]; };
    reportProgress?: boolean;
    responseType?: "json";
    withCredentials?: boolean;
  }
}
