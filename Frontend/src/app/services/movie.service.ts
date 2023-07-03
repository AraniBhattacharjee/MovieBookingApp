import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Movie } from '../models/movie.model';
import { Show } from '../models/show.model';

interface AllMovieResponse {
  movies: Movie[];
}

interface AllShowResponse {
  shows : Show[];
}

@Injectable({ providedIn: 'root' })
export class MovieService {
  MOVIE_CATALOG_SERVICE_URL: string =
    'http://localhost:8082/api/v1.0/moviebooking';

  constructor(private http: HttpClient) {}

  getAllMovies() {
    return this.http
      .get<AllMovieResponse>(this.MOVIE_CATALOG_SERVICE_URL + '/all')
      .pipe(catchError(this.handleError));
  }

  getMovieByName(movieName: string) {
    return this.http
      .get<AllShowResponse>(`${this.MOVIE_CATALOG_SERVICE_URL}/movies/search/${movieName}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(() => new Error(error.error.message));
  }
}
