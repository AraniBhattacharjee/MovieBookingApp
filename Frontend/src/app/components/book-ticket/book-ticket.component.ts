import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BookingService } from 'src/app/services/booking.service';
import { MaterialModule } from 'src/app/material.module';

@Component({
  selector: 'app-book-ticket',
  templateUrl: './book-ticket.component.html',
  styleUrls: ['./book-ticket.component.css'],
})
export class BookTicketComponent {
  seatsToBook: number = 1;

  constructor(
    public dialogRef: MatDialogRef<BookTicketComponent>,
    private ticketBookService: BookingService,
    @Inject(MAT_DIALOG_DATA) public data: { movieName: string, theatreName : string }
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  bookTickets(): void {
    if (this.seatsToBook > 0) {
      this.ticketBookService
        .bookTicket({
          movieName: this.data.movieName,
          theatreName : this.data.theatreName,
          seats: this.seatsToBook,
        })
        .subscribe({
          complete: () => {
            this.dialogRef.close('Ticket booking successfully!');
          },
          error: (errorMsg) => {
            this.dialogRef.close(errorMsg);
          },
        });
    }
  }
}
