import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/services/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.scss']
})
export class AdminHeaderComponent implements OnInit {

  constructor(public apiService: ApiService, public router: Router) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.apiService.post({ url: "/api-gateway/user-logout" }).subscribe(response => {
      this.router.navigate(["/"]);
    });
  }

}
