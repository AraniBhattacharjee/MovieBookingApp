import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { MovieService } from 'src/app/services/movie.service';
import { BookTicketComponent } from '../book-ticket/book-ticket.component';
import { Show } from 'src/app/models/show.model'
import { MaterialModule } from 'src/app/material.module';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css'],
})
export class MovieDetailsComponent {
  isLoading: boolean = false;
  movieName: string | null = '';
  shows: Show[] = [];
  userSubscription: Subscription = new Subscription();

  constructor(
    private authenticationService: AuthenticationService,
    private movieService: MovieService,
    private activeRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.movieName = this.activeRoute.snapshot.paramMap.get('movieName');
    if (this.movieName) {
      this.isLoading = true;
      this.movieService.getMovieByName(this.movieName).subscribe({
        next: (value) => {
          this.isLoading = false;
          this.shows = value.shows;
        },
        error: (errorMessage) => {
          this.isLoading = false;
          this.openSnackBar(errorMessage);
          this.router.navigate(['/home']);
        },
      });
    } else {
      console.log(this.movieName);
      this.openSnackBar('movie name is undefined');
      this.router.navigate(['/home']);
    }
  }

  bookTicket(movieName: string, theatreName: string) {
    this.userSubscription = this.authenticationService.user.subscribe(
      (user) => {
        if (user?.role === 'ADMIN') {
          this.openSnackBar("You don't have permission to perform this action");
        } else if (user?.role === 'CUSTOMER') this.openDialog(movieName, theatreName);
        else this.router.navigate(['/login']);
      }
    );
  }

  openDialog(movieName: string, theatreName: string) {
    const dialogRef = this.dialog.open(BookTicketComponent, {
      width: '300px',
      data: { movieName: movieName,
              theatreName : theatreName },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.openSnackBar(result);
      }
    });
  }

  openSnackBar(msg: string) {
    this.snackBar.open(msg, 'Ok', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2500,
    });
  }
}
