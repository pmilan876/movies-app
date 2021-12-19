package com.movies.db.repository;


import com.movies.db.entity.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistsRepository extends CrudRepository<Artist,Long> {
    Artist findByName(String name);
}
