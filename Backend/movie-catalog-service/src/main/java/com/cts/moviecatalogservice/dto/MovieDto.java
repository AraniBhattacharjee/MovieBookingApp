package com.cts.moviecatalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
	private String movieName;
	private int noOfTickets;
	private String theatreName;
}
