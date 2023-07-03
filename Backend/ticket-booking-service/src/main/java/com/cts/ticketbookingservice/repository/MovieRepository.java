package com.cts.ticketbookingservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cts.ticketbookingservice.model.Movie;

public interface MovieRepository extends MongoRepository<Movie, String>{

//	@Query("{{'movie' : :#{#movie}}, {'theatre' : :#{#theatre}}]}")
//	@Query("{{'movie' : ?0}, {'theatre' : ?1}}")
	Optional<Movie> findByMovieNameAndTheatreName(String movieName, String theatreName);
	
	Optional<List<Movie>> findByMovieName(String movieName);
	
}
