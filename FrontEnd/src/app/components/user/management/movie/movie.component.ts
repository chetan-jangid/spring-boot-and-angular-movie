import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AppUtilService } from 'src/app/services/app-util.service';
import { ApiService } from 'src/app/services/api.service';
import { StaticDataService, Pair } from 'src/app/services/static-data.service';
import { Movie } from 'src/app/models/movie';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.scss']
})
export class MovieComponent implements OnInit {

  form: FormGroup;
  actions: Pair[];
  movieCategories: Pair[];
  movieLanguages: Pair[];
  movieList: Movie[];

  constructor(
    private fb: FormBuilder,
    public appUtilService: AppUtilService,
    public apiService: ApiService,
    public staticDataService: StaticDataService) { }

  ngOnInit(): void {
    this.initializeForm("create");
    this.actions = [
      { label: "Create a new movie record", value: "create" },
      { label: "Modify existing movie details", value: "update" }
    ];
    this.setCategories();
    this.setLanguages();
    this.findExistingMovies();
  }

  setLanguages(): void {
    this.movieLanguages = this.staticDataService.getLanguages();
  }

  setCategories(): void {
    this.movieCategories = this.staticDataService.getCategories();
  }

  getAction(): string {
    return this.form.get("action").value;
  }

  saveOrUpdate(value: string): void {
    let url: string;
    if (value === "save") {
      url = "/movies/movie/save";
      this.form.get("id").clearValidators();
      this.form.get("id").updateValueAndValidity();
    } else {
      url = "/movies/movie/update";
      this.form.get("id").setValidators([Validators.required]);
      this.form.get("id").updateValueAndValidity();
    }
    this.appUtilService.checkFormValidations(this.form);
    if (this.form.valid) {
      this.apiService.post({ url: url, postBody: this.form.value }).subscribe(response => {
        this.appUtilService.dialog({ type: "success", title: response.responseMessage });
        this.findExistingMovies();
        this.initializeForm(this.form.get("action").value);
      }, error => this.appUtilService.dialog({ type: "error", title: error.errorMessage }) );
    }
  }

  delete(): void {
    let id: string = this.form.get("id").value;
    if (id) {
      let name: string = this.form.get("name").value;
      this.appUtilService.confirm({
        title: `Deleting a movie will remove its allocation from multiplex screens also. 
        Confirm delete ` + name + ` ?`,
        ok: () => { this.confirmDelete(); }
      });
    } else {
      this.appUtilService.dialog({ type: "warning", title: "Select a movie before deleting!" });
    }
  }

  confirmDelete(): void {
    this.apiService.post({ url: "/movies/movie/delete", postBody: this.form.value }).subscribe(response => {
      this.appUtilService.dialog({ type: "success", title: "Movie deleted successfully!" });
      this.initializeForm("update");
      this.findExistingMovies();
    }, error => this.appUtilService.dialog({ type: "error", title: error.errorMessage }) );
  }

  initializeForm(action: string): void {
    this.form = this.fb.group({
      id: [null],
      action: [action, Validators.required],
      name: [null, [Validators.required, Validators.maxLength(50)]],
      category: [null, Validators.required],
      producer: [null, [Validators.required, Validators.maxLength(200)]],
      director: [null, [Validators.required, Validators.maxLength(100)]],
      releaseDate: [null, Validators.required],
      language: ["hindi", Validators.required],
      description: [null, Validators.maxLength(500)]
    });
  }

  findExistingMovies(): void {
    this.movieList = [];
    this.apiService.get({ url: "/movies/movie/all-movies" }).subscribe(response => {
      if (response && response.movies.length > 0) {
        this.movieList = (response.movies as Array<any>);
      }
    });
  }

  setMovieDetails(): void {
    let id: string = this.form.get("id").value;
    if (id) {
      this.apiService.get({ url: "/movies/movie/find/" + id }).subscribe(response => {
        if (response) {
          this.form.get("name").setValue(response.name);
          this.form.get("category").setValue(response.category);
          this.form.get("producer").setValue(response.producer);
          this.form.get("director").setValue(response.director);
          this.form.get("releaseDate").setValue(this.appUtilService.toDate(response.releaseDate, "YYYY-MM-DD"));
          this.form.get("language").setValue(response.language);
          this.form.get("description").setValue(response.description);
        } else {
          this.initializeForm("update");
          this.appUtilService.dialog({ type: "error", title: "No such movie found!" });
          this.findExistingMovies();
        }
      }, error => {
        this.appUtilService.dialog({ type: "error", title: error.errorMessage });
        this.findExistingMovies();
      });
    }
  }

}
