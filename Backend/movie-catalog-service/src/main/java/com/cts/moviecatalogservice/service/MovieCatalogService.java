package com.cts.moviecatalogservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.moviecatalogservice.dto.AllMovieResponse;
import com.cts.moviecatalogservice.dto.AllShowResponse;
import com.cts.moviecatalogservice.dto.MovieDto;
import com.cts.moviecatalogservice.dto.ShowingDto;
import com.cts.moviecatalogservice.exceptions.ResourceNotFoundException;
import com.cts.moviecatalogservice.model.Movie;
import com.cts.moviecatalogservice.repository.MovieRepository;


@Service
public class MovieCatalogService {
	@Autowired
	private MovieRepository movieRepository;

	@Transactional(readOnly = true)
	public AllMovieResponse getAllMovies() {
		//Retrieve all movies
		List<Movie> movies = movieRepository.findAll();
		
		//Map every movie object to MovieDto
		List<MovieDto> dto = movies.stream()
									.map(m-> MovieDto.builder().movieName(m.getMovieName()).theatreName(m.getTheatreName())
									.noOfTickets(m.getNoOfTickets())
									.build()).collect(Collectors.toList());
		
		return AllMovieResponse.builder().movies(dto).build();
	}

	@Transactional(readOnly = true)
	public AllShowResponse getMovieByMovieName(String movieName) {
		//Retrieve movie
		List<Movie> movies = movieRepository.findByMovieName(movieName)
				.orElseThrow(() -> new ResourceNotFoundException("No movie found with name: " + movieName));
		
		//Map every show related to a movie to ShowingDto
		List<ShowingDto> dto = movies.stream()
								 .map(m->ShowingDto.builder().movieName(m.getMovieName())
								 .theatreName(m.getTheatreName()).totalSeatsAvailable(m.getNoOfTickets()).build())
								 .collect(Collectors.toList());
		
		return AllShowResponse.builder().shows(dto).build();
	}

}
