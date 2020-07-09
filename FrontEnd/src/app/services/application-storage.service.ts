import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApplicationStorageService {

  readonly KEYS = {
    AUTH_TOKEN: "AUTH_TOKEN"
  };

  constructor() { }

  set(key: string, value: string): void {
    sessionStorage.setItem(key, value);
  }

  get(key: string): string {
    return sessionStorage.getItem(key);
  }

  remove(key: string): void {
    sessionStorage.removeItem(key);
  }

  clear(): void {
    sessionStorage.clear();
  }

}
