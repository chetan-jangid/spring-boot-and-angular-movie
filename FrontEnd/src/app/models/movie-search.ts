import { Multiplex } from './multiplex';

export interface MovieWrapper {
  movieId: number;
  movieName: string;
  releaseDate: string;
  releaseYear: number;
  multiplexes: Multiplex[];
}
