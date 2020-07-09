import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { StaticDataService } from 'src/app/services/static-data.service';
import { ApiService } from 'src/app/services/api.service';
import { AppUtilService } from 'src/app/services/app-util.service';
import { Movie } from 'src/app/models/movie';
import { Multiplex } from 'src/app/models/multiplex';
import { MovieAllocationScreen } from 'src/app/models/movie-allocation-screen';

@Component({
  selector: 'app-movie-allocation',
  templateUrl: './movie-allocation.component.html',
  styleUrls: ['./movie-allocation.component.scss']
})
export class MovieAllocationComponent implements OnInit {

  form: FormGroup;
  movieList: Movie[];
  multiplexes: Multiplex[];
  screens: MovieAllocationScreen[];

  constructor(
    private fb: FormBuilder,
    public appUtilService: AppUtilService,
    public apiService: ApiService,
    public staticDataService: StaticDataService) { }

  ngOnInit(): void {
    this.initializeForm();
    this.getMovies();
    this.getMultiplexes();
  }

  initializeForm(): void {
    this.form = this.fb.group({
      movieId: [null, Validators.required],
      multiplexId: [null, Validators.required],
      screenId: [null, Validators.required]
    });
  }

  getButtonText(): string {
    return "Allocate";
  }

  allocate(): void {
    this.appUtilService.checkFormValidations(this.form);
    if (this.form.valid) {
      this.apiService.post({
        url: "/multiplex/movie-allocation/allocate",
        postBody: this.form.value
      }).subscribe(response => {
        this.appUtilService.dialog({ type: "success", title: response.responseMessage });
        this.resetForm();
      }, error => this.appUtilService.dialog({ type: "error", title: error.errorMessage }) );
    }
  }

  resetForm(): void {
    this.form.reset();
    this.initializeForm();
    this.screens = [];
  }

  getMovies(): void {
    this.movieList = [];
    this.apiService.get({ url: "/movies/movie/all-movies" }).subscribe(response => {
      if (response && response.movies.length > 0) {
        this.movieList = (response.movies as Array<any>);
      }
    });
  }

  getMultiplexes(): void {
    this.multiplexes = [];
    this.apiService.get({ url: "/multiplex/manage/all-multiplex" }).subscribe(response => {
      if (response && response.multiplexes.length > 0) {
        this.multiplexes = (response.multiplexes as Array<any>);
      }
    });
  }

  findScreenDetails(): void {
    let multplexId: string = this.form.get("multiplexId").value;
    this.screens = [];
    if (multplexId) {
      this.apiService.get({
        url: "/multiplex/movie-allocation/find-allocation/" + multplexId
      }).subscribe(response => {
        this.screens = response.screens;
      });
    }
  }

}
