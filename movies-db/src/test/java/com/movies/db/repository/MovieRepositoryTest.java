package com.movies.db.repository;

import com.movies.db.entity.Artist;
import com.movies.db.entity.Movie;
import com.movies.db.entity.MovieArtist;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ArtistsRepository artistsRepository;
    @Autowired
    MovieArtistRepository movieArtistRepository;

    @Test
    public void findAllMovie() {
        Iterable<Movie> movies = movieRepository.findAll();
        assertThat(movies).isEmpty();
    }

    @Test
    public void saveMovie() {
        System.out.println("Running Saved Movie Test");
        Movie savedMovie = movieRepository.save(Movie.builder().title("Rambo").genre("Action").releaseYear(1998).imdbRating(10).build());
        assertNotNull(savedMovie.getId());

        Movie movie = movieRepository.findById(savedMovie.getId()).get();
        assertThat(movie).hasFieldOrPropertyWithValue("id", savedMovie.getId());
        assertThat(movie).hasFieldOrPropertyWithValue("title", "Rambo");
        assertThat(movie).hasFieldOrPropertyWithValue("genre", "Action");
        assertThat(movie).hasFieldOrPropertyWithValue("releaseYear", 1998);
        assertThat(movie).hasFieldOrPropertyWithValue("imdbRating", 10);

    }

    @Test
    public void findMovie() {
        Movie movie1 = movieRepository.save(Movie.builder().title("Rambo").genre("Action").releaseYear(1998).imdbRating(10).build());
        assertNotNull(movie1.getId());
        entityManager.persist(movie1);
        Iterable<Movie> movies = movieRepository.findAll();
        assertThat(movies).hasSize(1).contains(movie1);
    }

    @Test
    public void deleteAllMovie() {
        Movie movie1 = movieRepository.save(Movie.builder().title("Rambo").genre("Action").releaseYear(1998).imdbRating(10).build());
        assertNotNull(movie1.getId());
        entityManager.persist(movie1);
        Movie movie2 = movieRepository.save(Movie.builder().title("Jumanji").genre("Action").releaseYear(1999).imdbRating(10).build());
        assertNotNull(movie2.getId());
        entityManager.persist(movie2);
        Movie movie3 = movieRepository.save(Movie.builder().title("Titanic").genre("Action").releaseYear(2000).imdbRating(10).build());
        assertNotNull(movie3.getId());
        entityManager.persist(movie3);
        movieRepository.deleteAll();
    }
    @Test
    public void delete_movie_by_id(){
        Movie movie1 = movieRepository.save(Movie.builder().title("Rambo").genre("Action").releaseYear(1998).imdbRating(10).build());
        assertNotNull(movie1.getId());
        entityManager.persist(movie1);
        Movie movie2 = movieRepository.save(Movie.builder().title("Jumanji").genre("Action").releaseYear(1999).imdbRating(10).build());
        assertNotNull(movie2.getId());
        entityManager.persist(movie2);
        movieRepository.deleteById(movie1.getId());
        Iterable<Movie> movies = movieRepository.findAll();
        assertThat(movies).hasSize(1).contains(movie2);
    }
    @Test
    public void update_movie_by_id(){
        Movie movie1 = movieRepository.save(Movie.builder().title("Rambo").genre("Action").releaseYear(1998).imdbRating(10).build());
        assertNotNull(movie1.getId());
        entityManager.persist(movie1);
        Movie updatedMovie = movieRepository.save(Movie.builder().title("Hunger Games").genre("Action").releaseYear(2010).imdbRating(10).build());
        assertNotNull(updatedMovie.getId());
        entityManager.persist(updatedMovie);
        Movie movie = movieRepository.findById(movie1.getId()).get();
        movie.setId(updatedMovie.getId());
        movie.setTitle(updatedMovie.getTitle());
        movie.setGenre(updatedMovie.getGenre());
        movie.setReleaseYear(updatedMovie.getReleaseYear());
        movie.setImdbRating(updatedMovie.getImdbRating());
        movieRepository.save(movie);
        Movie checkMovie = movieRepository.findById(movie1.getId()).get();
        assertThat(checkMovie.getId()).isEqualTo(movie1.getId());
        assertThat(checkMovie.getTitle()).isEqualTo(movie1.getTitle());
        assertThat(checkMovie.getGenre()).isEqualTo(movie1.getGenre());
        assertThat(checkMovie.getReleaseYear()).isEqualTo(movie1.getReleaseYear());
        assertThat(checkMovie.getImdbRating()).isEqualTo(movie1.getImdbRating());
    }
    @Test
    public void findAllArtist(){
        Iterable<Artist> artists = artistsRepository.findAll();
        assertThat(artists).isEmpty();
    }
    @Test
    public void saveArtist(){
        Artist savedArtist = artistsRepository.save(Artist.builder().name("Rock").build());
        assertNotNull(savedArtist.getId());

        Artist artist = artistsRepository.findById(savedArtist.getId()).get();
        assertThat(artist).hasFieldOrPropertyWithValue("id",savedArtist.getId());
        assertThat(artist).hasFieldOrPropertyWithValue("name","Rock");


    }
    @Test
    public void findArtist(){
        Artist artist = artistsRepository.save(Artist.builder().name("Rock").build());
        assertNotNull(artist.getId());
        entityManager.persist(artist);
        Iterable<Artist> artists = artistsRepository.findAll();
        assertThat(artists).hasSize(1).contains(artist);
    }
    @Test
    public void delete_all_artists(){
        Artist artist1 = artistsRepository.save(Artist.builder().name("Rock").build());
        assertNotNull(artist1.getId());
        entityManager.persist(artist1);
        Artist artist2 = artistsRepository.save(Artist.builder().name("Dwayne").build());
        assertNotNull(artist2.getId());
        entityManager.persist(artist2);
        Artist artist3 = artistsRepository.save(Artist.builder().name("zzz").build());
        assertNotNull(artist3.getId());
        entityManager.persist(artist3);
        artistsRepository.deleteAll();
    }
    @Test
    public void delete_artist_by_id(){
        Artist artist1 = artistsRepository.save(Artist.builder().name("Rock").build());
        assertNotNull(artist1.getId());
        entityManager.persist(artist1);
        Artist artist2 = artistsRepository.save(Artist.builder().name("dwayne").build());
        assertNotNull(artist2.getId());
        entityManager.persist(artist2);
        artistsRepository.deleteById(artist1.getId());
        Iterable<Artist> artists = artistsRepository.findAll();
        assertThat(artists).hasSize(1).contains(artist2);
    }
}