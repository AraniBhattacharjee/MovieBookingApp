package com.cts.adminservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddMovieRequest {
	
	@NotBlank(message="Movie name should not be blank")
	private String movieName;
	@NotBlank(message="Theatre name should not be blank")
	private String theatreName;
	@NotNull(message="Number of tickets should not be blank")
	private int noOfTickets;
}
