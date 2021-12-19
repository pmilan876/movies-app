package com.movies.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String title;
    private String genre;
    private int releaseYear;
    private float imdbRating;
    private String imageUrl;
    private String movieRating;
    private String runTime;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "movie",fetch = FetchType.LAZY)
    private Set<MovieArtist> movieArtists = new HashSet<>();

}
