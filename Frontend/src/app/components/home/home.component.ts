import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Movie } from 'src/app/models/movie.model';
import { MovieService } from 'src/app/services/movie.service';
import { MaterialModule } from 'src/app/material.module';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  isLoading: boolean = false;
  searchQuery: string = '';
  movies: Movie[] = [];
  movieNames : string[] = [];

  constructor(
    private movieService: MovieService,
    private snackBar: MatSnackBar,
    private route : Router
  ) {}

  ngOnInit() {
    this.isLoading = true;
    this.movieService.getAllMovies().subscribe({
      next: (value) => {
        this.movies = value.movies;
        console.log(this.movies);
        this.isLoading = false;
        console.log(this.isLoading);
        for (let index = 0; index < this.movies.length; index++) {
          if(!this.movieNames.includes(this.movies[index].movieName))
            this.movieNames.push(this.movies[index].movieName);
        }
      },
      error: (errorMessage) => {
        this.isLoading = false;
        this.openSnackBar(errorMessage);
      },
    });
  }

  get filteredMovies() {

    if(this.searchQuery ===null){
      return this.movieNames;
    }
    return this.movieNames.filter(movie =>
      movie.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }

  openSnackBar(msg: string) {
    this.snackBar.open(msg, 'Ok', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2500,
    });
  }
}
