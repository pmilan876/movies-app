package com.movies.db.service;

import com.google.common.collect.Lists;
import com.movies.db.dto.MovieDetail;
import com.movies.db.entity.Artist;
import com.movies.db.entity.Movie;
import com.movies.db.entity.MovieArtist;
import com.movies.db.repository.ArtistsRepository;
import com.movies.db.repository.MovieArtistRepository;
import com.movies.db.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ArtistsRepository artistsRepository;

    @Autowired
    MovieArtistRepository movieArtistRepository;

    public List<Movie> getAll() {
        return Lists.newArrayList(movieRepository.findAll());
    }

    public Movie getById(Long id) {
        return movieRepository.findById(id).get();
    }
    public Movie getByTitle(String title) {
        return movieRepository.findByTitleIgnoreCase(title);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public Movie update(Movie movie) {
        return movieRepository.save(movie);
    }


    public List<Artist> getAllArtist() {
        return Lists.newArrayList(artistsRepository.findAll());
    }

    public void deleteArtistById(Long id) {
        artistsRepository.deleteById(id);
    }

    public void deleteAllArtists() {
        artistsRepository.deleteAll();
    }

    public Artist saveArtist(Artist artist) {
        return artistsRepository.save(artist);
    }

    public Artist getArtistById(Long id) {
        return artistsRepository.findById(id).get();
    }

    public Artist updateArtist(Artist artist) {
        return artistsRepository.save(artist);
    }

    public MovieArtist addArtistToMovie(MovieArtist movieArtist) {
        return movieArtistRepository.save(movieArtist);
    }
    public void deleteMovieArtistById(Long id){
        movieArtistRepository.deleteById(id);
    }

    public List<MovieArtist> getMovieArtists(Long movieId){
        return movieArtistRepository.findByMovieId(movieId);
    }

    public MovieDetail getMovieDetail(Long movieId) {
        Movie movie = getById(movieId);
        List<MovieArtist> movieArtists = movieArtistRepository.findByMovieId(movieId);
        List<Artist> directors = new ArrayList<>();
        List<Artist> actors = new ArrayList<>();
        movieArtists.stream().forEach(ma-> {
            if(ma.getRole().equals("Director")){
                directors.add(ma.getArtist());
            }
            else if(ma.getRole().equals("Actor")){
                actors.add(ma.getArtist());
            }

        });
        MovieDetail movieDetail = MovieDetail.builder().movie(movie).directors(directors).actors(actors).build();
        return movieDetail;
    }

    public Artist getArtistByName(String name) {
        return artistsRepository.findByName(name);
    }
}
