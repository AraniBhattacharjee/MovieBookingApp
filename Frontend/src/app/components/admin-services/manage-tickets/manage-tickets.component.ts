import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Ticket } from 'src/app/models/ticket.model';
import { AdminService } from 'src/app/services/admin.service';
import { MaterialModule } from 'src/app/material.module';

export interface DialogData {
  status: string;
}

@Component({
  selector: 'app-manage-tickets',
  templateUrl: './manage-tickets.component.html',
  styleUrls: ['./manage-tickets.component.css']
})
export class ManageTicketsComponent {

  tickets : Ticket[] = [];
  isLoading = false;
  status : string = "";

  constructor(
    private adminService: AdminService,
    private snackBar: MatSnackBar,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.isLoading = true;
    this.adminService.getAllTickets().subscribe({
      next: (value) => {
        this.tickets = value.tickets;
        this.isLoading = false;
      },
      error: (errorMessage) => {
        this.isLoading = false;
        this.openSnackBar(errorMessage);
      },
    });
  }

  openSnackBar(msg: string) {
    this.snackBar.open(msg, 'Ok', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2500,
    });
  }

  openDialog(id : string) {

    const dialogRef = this.dialog.open(UpdateStatusComponent,
      {
        data: {status: this.status}
      });

    dialogRef.afterClosed().subscribe(result => {
      this.adminService.updateTicketStatus(id, this.status);
    });
  }

}
@Component({
  selector: 'update-status-dialog',
  templateUrl: 'update-status-dialog.html',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, MaterialModule],
})
export class UpdateStatusComponent {
  constructor(
    public dialogRef: MatDialogRef<UpdateStatusComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

}
