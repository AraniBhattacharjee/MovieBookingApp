package com.cts.moviecatalogservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cts.moviecatalogservice.dto.AllMovieResponse;
import com.cts.moviecatalogservice.dto.AllShowResponse;
import com.cts.moviecatalogservice.dto.ErrorResponse;
import com.cts.moviecatalogservice.dto.MovieDto;
import com.cts.moviecatalogservice.service.MovieCatalogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Movie Catalog", description = "movie API")
@RestController()
public class MovieCatalogController {
	@Autowired
	private MovieCatalogService catalogService;

	@Operation(summary = "View all movies", description = "Get list of currently available movies in theaters")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All movie retrieve successful", content = @Content(schema = @Schema(implementation = AllMovieResponse.class))) })
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/all")
	public ResponseEntity<AllMovieResponse> getAllMovies() {
		AllMovieResponse response = catalogService.getAllMovies();
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Search movie by name", description = "Get specific movie details by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movie details retrieve successful", content = @Content(schema = @Schema(implementation = MovieDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	@GetMapping("/movies/search/{movieName}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<AllShowResponse> getMovieByName(
			@Parameter(description = "Name of the movie", required = true) @PathVariable String movieName) {
		AllShowResponse movie = catalogService.getMovieByMovieName(movieName);
		return ResponseEntity.ok(movie);
	}
}
