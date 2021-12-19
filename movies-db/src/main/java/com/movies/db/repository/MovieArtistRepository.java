package com.movies.db.repository;

import com.movies.db.entity.MovieArtist;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieArtistRepository extends CrudRepository<MovieArtist, Long> {
    public List<MovieArtist> findByMovieId(Long id);
}
