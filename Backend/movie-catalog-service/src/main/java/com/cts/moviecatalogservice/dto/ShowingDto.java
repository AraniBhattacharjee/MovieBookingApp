package com.cts.moviecatalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowingDto {
	private String movieName;
	private String theatreName;
	private int totalSeatsAvailable;
}
