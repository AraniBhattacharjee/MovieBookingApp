package com.cts.ticketbookingservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.ticketbookingservice.dto.BookTicketRequest;
import com.cts.ticketbookingservice.dto.Response;
import com.cts.ticketbookingservice.dto.TicketsDTO;
import com.cts.ticketbookingservice.dto.ValidationDto;
import com.cts.ticketbookingservice.exceptions.InvalidTokenException;
import com.cts.ticketbookingservice.exceptions.ResourceNotFoundException;
import com.cts.ticketbookingservice.feign.AuthClient;
import com.cts.ticketbookingservice.model.Movie;
import com.cts.ticketbookingservice.model.TicketBooking;
import com.cts.ticketbookingservice.model.User;
import com.cts.ticketbookingservice.repository.MovieRepository;
import com.cts.ticketbookingservice.repository.TicketBookingRepository;
import com.cts.ticketbookingservice.repository.UserRepository;

@Service
public class TicketBookingService {
	@Autowired
	private AuthClient authClient;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private TicketBookingRepository ticketBookingRepository;
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public Response bookTicket(String token, BookTicketRequest request) {
		String invalidMsg = "Invalid Token";
		try {
			ValidationDto authResponse = authClient.validateAuthToken(token);
			if (authResponse.isStatus()) {

				// Only CUSTOMER can perform the book ticket operation
				if (authResponse.getRole().equals("CUSTOMER")) {
					
					List<Movie> m = movieRepository.findByMovieName(request.getMovieName())
							.orElseThrow(() -> new ResourceNotFoundException("No show found for given movie or theatre"));


					// Retrieve the movie details
					Movie movie = movieRepository.findByMovieNameAndTheatreName(request.getMovieName(), request.getTheatreName())
							.orElseThrow(() -> new ResourceNotFoundException("No show found for given movie or theatre"));

					// If requested seat count is less than or equal to remaining seats
					if (movie.getNoOfTickets()- request.getSeats() >= 0) {
						movie.setNoOfTickets(movie.getNoOfTickets()- request.getSeats());

						// Retrieve the user details
						User user = userRepository.findById(authResponse.getUserId()).orElse(null);

						TicketBooking ticket = TicketBooking.builder().id(generateRandomId()).userId(user.getUserId())
								.movieName(movie.getMovieName()).theatreName(movie.getTheatreName())
								.numSeats(request.getSeats()).status("CONFIRM").build();

						// Save the ticket and show record
						ticketBookingRepository.save(ticket);
						movieRepository.save(movie);

						// Return response
						return Response.builder().status("success").message("Ticket booking successfull").build();
					} else
						throw new RuntimeException("Ticket not available!");
				} else
					throw new InvalidTokenException("Only a customer can perform ticket book");
			} else
				throw new InvalidTokenException(invalidMsg);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public TicketsDTO viewTickets(String token){
		String invalidMsg = "Invalid Token";
		try {
			ValidationDto authResponse = authClient.validateAuthToken(token);
			if (authResponse.isStatus()) {

				// Only ADMIN can perform the view tickets operation
				if (authResponse.getRole().equals("ADMIN")) {
					
					//Retrieve all tickets
					List<TicketBooking> t = ticketBookingRepository.findAll();

						// Return response
						return TicketsDTO.builder().tickets(t).build();
				} else
					throw new InvalidTokenException("Only a admin can perform ticket book");
			} else
				throw new InvalidTokenException(invalidMsg);
		} catch (Exception e) {
			throw e;
		}
	}

	private String generateRandomId() {
		return "TB" + UUID.randomUUID().toString();
	}

}
