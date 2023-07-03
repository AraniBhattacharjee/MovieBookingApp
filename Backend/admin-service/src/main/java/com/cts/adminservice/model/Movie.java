package com.cts.adminservice.model;

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
@Document(collection = "movies")
public class Movie {

	@Id
	private String id;
	@Field("movie_name")
	private String movieName;
	@Field("theatre_name")
	private String theatreName;
	@Field("no_of_tickets")
	private int noOfTickets;
}