import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StaticDataService {

  constructor() { }

  private categories: Array<Pair> = [
    { value: "comedy", label: "Comedy" },
    { value: "scifi", label: "Sci-Fi" },
    { value: "horror", label: "Horror" },
    { value: "romance", label: "Romance" },
    { value: "action", label: "Action" },
    { value: "thriller", label: "Thriller" },
    { value: "drama", label: "Drama" },
    { value: "mystery", label: "Mystery" },
    { value: "crime", label: "Crime" },
    { value: "animation", label: "Animation" },
    { value: "adventure", label: "Adventure" },
    { value: "motivational", label: "Motivational" },
    { value: "comedyromance", label: "Comedy-Romance" },
    { value: "comedydrama", label: "Comedy-Drama" },
    { value: "actioncomedy", label: "Action-Comedy" },
    { value: "superhero", label: "Superhero" }
  ];
  
  private languages: Array<Pair> = [
    { value: "english", label: "English" },
    { value: "hindi", label: "Hindi" },
    { value: "marathi", label: "Marathi" }
  ];

  getCategories(): Array<Pair> { return this.categories; }
  getLanguages(): Array<Pair> { return this.languages; }

}

export interface Pair {
  label: string;
  value: any;
}
