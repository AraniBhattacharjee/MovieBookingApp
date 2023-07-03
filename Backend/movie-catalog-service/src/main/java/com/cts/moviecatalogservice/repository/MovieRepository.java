package com.cts.moviecatalogservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cts.moviecatalogservice.model.Movie;

public interface MovieRepository extends MongoRepository<Movie, String>{

	Optional<List<Movie>> findByMovieName(String movieName);
	
}
