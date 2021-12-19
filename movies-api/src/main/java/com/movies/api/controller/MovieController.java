package com.movies.api.controller;


import com.movies.db.dto.MovieDetail;
import com.movies.db.entity.Artist;
import com.movies.db.entity.MovieArtist;
import com.movies.db.service.MovieService;
import com.movies.db.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class MovieController {
    @Autowired
    MovieService movieService;

    @GetMapping(path = "/movies")
    public List<Movie> getAll() {
        List<Movie> movies = movieService.getAll();

        return movies;
    }

    @GetMapping(path = "/movies/{id}")
    public Movie getById(@PathVariable("id") Long id) {
        Movie movie = movieService.getById(id);
        return movie;
    }

    @PostMapping(path = "/movies")
    public Movie save(@RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @DeleteMapping(path = "/movies/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        movieService.delete(id);
        return "done";
    }

    @DeleteMapping(path = "/movies")
    public String deleteAllMovies() {
        movieService.deleteAll();
        return "done";
    }

    @PutMapping(path = "/movies")
    public Movie update(@RequestBody Movie movie) {
        return movieService.update(movie);
    }


    @GetMapping(path = "/artists")
    public List<Artist> getAllArtists() {
        List<Artist> artists = movieService.getAllArtist();
        return artists;
    }

    @GetMapping(path = "/artists/{id}")
    public Artist getArtistById(@PathVariable("id") Long id) {
        return movieService.getArtistById(id);
    }

    @PostMapping(path = "/artists")
    public Artist save(@RequestBody Artist artist) {
        return movieService.saveArtist(artist);
    }

    @DeleteMapping(path = "/artists/{id}")
    public String deleteArtistById(@PathVariable("id") Long id) {
        movieService.deleteArtistById(id);
        return "done";
    }

    @DeleteMapping(path = "/artists")
    public String deleteAllArtists() {
        movieService.deleteAllArtists();
        return "done";
    }

    @PutMapping(path = "/artists")
    public Artist updateArtist(@RequestBody Artist artist) {
        return movieService.updateArtist(artist);
    }
    @GetMapping(path = "/movieartist/movie/{movieId}")
    public List<MovieArtist> addMovieArtist(@PathVariable("movieId") Long movieId) {
        return movieService.getMovieArtists(movieId);

    }
    @PostMapping(path = "/movieartist")
    public String addMovieArtist(@RequestBody MovieArtist movieArtist) {
         movieService.addArtistToMovie(movieArtist);
         return "done";
    }
    @GetMapping(path = "/moviedetail/{id}")
    public MovieDetail getMovieDetail(@PathVariable("id") Long id) {
       return movieService.getMovieDetail(id);
    }

    @DeleteMapping(path = "/movieartist/{id}")
    public String deleteMovieArtistById(@PathVariable("id") Long id) {
        movieService.deleteMovieArtistById(id);
        return "done";
    }
}
