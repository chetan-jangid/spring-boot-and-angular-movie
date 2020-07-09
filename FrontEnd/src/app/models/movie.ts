export interface MovieResponse {
  movies?: Movie[];
}

export interface Movie {
  id?: string;
	name: string;
	description?: string;
	language?: string;
	category?: string;
	producer?: string;
  director?: string;
}
