package com.movies.db.repository;


import com.movies.db.entity.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {


    Movie findByTitleIgnoreCase(String name);
    Movie findByTitleIgnoreCaseAndReleaseYear(String name, int releaseYear);


}
