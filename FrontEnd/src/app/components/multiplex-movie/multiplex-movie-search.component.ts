import { Component, OnInit } from '@angular/core';
import { Pair } from 'src/app/services/static-data.service';
import { ApiService } from 'src/app/services/api.service';
import { MovieWrapper } from 'src/app/models/movie-search';
import { AppUtilService } from 'src/app/services/app-util.service';
import { Multiplex } from 'src/app/models/multiplex';

@Component({
  selector: 'app-multiplex-movie-search',
  templateUrl: './multiplex-movie-search.component.html',
  styleUrls: ['./multiplex-movie-search.component.scss']
})
export class MultiplexMovieSearchComponent implements OnInit {

  searchType: string;
  searchTypes: Pair[];
  searchParams: SearchParams;
  movieResult: MovieWrapper[];
  multiplexResult: Multiplex[];

  constructor(
    public apiService: ApiService,
    public appUtilService: AppUtilService) {}

  ngOnInit(): void {
    this.searchParams = {
      searchText: ""
    };
    this.searchTypes = [
      { label: "Movie", value: SearchType.MOVIE },
      { label: "Multiplex", value: SearchType.MULTIPLEX }
    ];
    this.searchType = "movie";
    this.setSearchCriteria();
  }

  setSearchCriteria(): void {
    this.movieResult = [];
    this.multiplexResult = [];
    this.searchParams.searchText = "";
    if (this.searchType === SearchType.MOVIE) {
      this.searchParams.searchPlaceholder = "Search By Movie";
    } else {
      this.searchParams.searchPlaceholder = "Search By Multiplex";
    }
  }

  search(event: KeyboardEvent): void {
    //call only for letters or numbers, backspace, enter and delete keys
    let keyCode: number = event.which;
    if ((keyCode <= 90 && keyCode >= 48) || keyCode == 8 || keyCode == 13 || keyCode == 46) {
      this.movieResult = [];
      this.multiplexResult = [];
      if (this.searchParams.searchText) {
        if (this.searchType == SearchType.MOVIE) {
          this.searchByMovie();
        } else {
          this.searchByMultiplex();
        }
      }
    }
  }

  searchByMovie(): void {
    this.apiService.get({ url: "/movies/movie-api/find/" + this.searchParams.searchText, options: {
      observe: "body"
    } }).subscribe(response => {
      if (response && response.data) {
        this.movieResult = response.data as MovieWrapper[];
        this.movieResult.forEach(m => 
          m.releaseYear = this.appUtilService.getYear(this.appUtilService.toDate(m.releaseDate, "YYYY-MM-DD")));
      }
    });
  }

  searchByMultiplex(): void {
    this.apiService.get({ url: "/multiplex/search/by-name/" + this.searchParams.searchText }).subscribe(response => {
      if (response && response.multiplexes) {
        this.multiplexResult = response.multiplexes as Multiplex[];
      }
    });
  }

}

interface SearchParams {
  searchText?: string;
  searchPlaceholder?: string;
}

enum SearchType {
  MOVIE = "movie",
  MULTIPLEX = "multiplex"
}
