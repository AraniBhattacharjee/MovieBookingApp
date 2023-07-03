import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AuthGuard } from './guards/auth.guard';
import { MovieDetailsComponent } from './components/movie-details/movie-details.component';
import { ManageMoviesComponent } from './components/admin-services/manage-movies/manage-movies.component';
import { AddMovieComponent } from './components/admin-services/add-movie/add-movie.component';
import { ManageTicketsComponent } from './components/admin-services/manage-tickets/manage-tickets.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'details/:movieName', component: MovieDetailsComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  {
    path: 'admin/manage-movies',
    component: ManageMoviesComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'admin/add-new-movie',
    component: AddMovieComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'admin/manage-tickets',
    component: ManageTicketsComponent,
    canActivate: [AuthGuard],
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
