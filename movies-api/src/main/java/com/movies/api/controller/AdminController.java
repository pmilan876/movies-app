package com.movies.api.controller;

import com.movies.db.entity.Artist;
import com.movies.db.entity.Movie;
import com.movies.db.entity.MovieArtist;
import com.movies.db.service.MovieService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/load")
public class AdminController {
    @Autowired
    private MovieService movieService;

    @PostMapping(path = "/movie")
    public String insertMovie(@RequestBody JSONObject movieDetail) {
        Movie movie = persistMovie(movieDetail);

        List<String> actorNames = (List<String>) movieDetail.get("actors");

        List<Artist> actors = new ArrayList<>();
        for(String name : actorNames){
            actors.add(persistArtist(name));
        }
        List<String> directorNames = (List<String>) movieDetail.get("directors");

        List<Artist> directors = new ArrayList<>();
        for(String name : directorNames){
            directors.add(persistArtist(name));
        }

        for(Artist director : directors){
            addArtist(movie, director, "Director");
        }
        for(Artist actor : actors){
            addArtist(movie, actor, "Actor");
        }
        return "done";


    }

    private void addArtist(Movie movie, Artist artist, String role) {
        MovieArtist movieArtist = new MovieArtist();
        movieArtist.setMovie(movie);
        movieArtist.setArtist(artist);
        movieArtist.setRole(role);
        movieService.addArtistToMovie(movieArtist);
    }

    private Movie persistMovie(JSONObject movieDetail) {
        Movie movie = new Movie();
        movie.setTitle((String) movieDetail.get("title"));
        movie.setImdbRating(Float.parseFloat((String) movieDetail.get("imdb_rating")));
        movie.setRunTime((String) movieDetail.get("run_time"));
        movie.setDescription((String) movieDetail.get("description"));
        movie.setMovieRating((String) movieDetail.get("movie_rating"));
        movie.setReleaseYear(Integer.parseInt((String) movieDetail.get("release_year")));
        movie.setImageUrl((String) movieDetail.get("image_url"));
        movie.setGenre((String) movieDetail.get("genre"));
        movie = movieService.save(movie);
        return movie;
    }
    private Artist persistArtist(String name){
        Artist artist = new Artist();
        artist.setName(name);
        try{
            artist = movieService.saveArtist(artist);
        }
        catch(Exception e){
            e.printStackTrace();
            artist = movieService.getArtistByName(artist.getName());
        }
        return artist;
    }
}
