package com.cts.adminservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.InvalidIsolationLevelException;

import com.cts.adminservice.dto.AddMovieRequest;
import com.cts.adminservice.dto.ShowingDto;
import com.cts.adminservice.dto.ValidationDto;
import com.cts.adminservice.exceptions.InvalidTokenException;
import com.cts.adminservice.exceptions.ResourceNotFoundException;
import com.cts.adminservice.feign.AuthClient;
import com.cts.adminservice.model.Movie;
import com.cts.adminservice.model.TicketBooking;
import com.cts.adminservice.repository.MovieRepository;
import com.cts.adminservice.repository.TicketBookingRepository;

@Service
public class AdminService {
	@Autowired
	private AuthClient authClient;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private TicketBookingRepository ticketBookingRepository;
	@Autowired
	private MovieProducer movieProducer;

	public void updateTicketStatus(String token, String ticketId, String newStatus) {
		if (isAdmin(token)) {
			TicketBooking ticket = ticketBookingRepository.findById(ticketId)
					.orElseThrow(() -> new ResourceNotFoundException("No ticket found with id: " + ticketId));
			ticket.setStatus(newStatus);
			ticketBookingRepository.save(ticket);

		} else
			throw new InvalidTokenException("Only admin can update ticket status");
	}

	public void addMovie(String token, AddMovieRequest request) {
		if (isAdmin(token)) {
			// Create movie entity object from the request
			Movie movie = Movie.builder().movieName(request.getMovieName())
						  .theatreName(request.getTheatreName()).noOfTickets(request.getNoOfTickets())
					      .build();

			// Save the movie in the database
			movieRepository.save(movie);

			// Create the new movie creation kafka event
			movieProducer.sendMessage("New movie created. Id: " + movie.getId());
			
		} else
			throw new InvalidIsolationLevelException("Only admin can add new movie");
	}

	public void deleteMovie(String token, String movieId) {
		if (isAdmin(token)) {
			Movie movie = movieRepository.findById(movieId)
					.orElseThrow(() -> new ResourceNotFoundException("No movie found with id: " + movieId));
			movieRepository.delete(movie);
		} else
			throw new InvalidTokenException("Only admin can perform delete movie action");
	}

	private boolean isAdmin(String token) {
		ValidationDto authResponse = authClient.validateAuthToken(token);
		return authResponse.isStatus() && authResponse.getRole().equals("ADMIN");
	}

	private String generateRandomId() {
		return UUID.randomUUID().toString();
	}
}
