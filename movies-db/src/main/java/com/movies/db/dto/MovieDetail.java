package com.movies.db.dto;

import com.movies.db.entity.Artist;
import com.movies.db.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDetail {
    private Movie movie;
    private List<Artist> directors;
    private List<Artist> actors;

}
