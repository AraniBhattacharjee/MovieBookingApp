package com.cts.ticketbookingservice.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "tickets")
public class TicketBooking {
	@Id
	@Field("_id")
	private String id;

	@Field("user_id")
	private String userId;

	@Field("movie_name")
	private String movieName;
	
	@Field("theatre_name")
	private String theatreName;
	
	@Field("num_seats")
	private int numSeats;

	@Field("ticket_seats")
	private String status;
}
