package org.example.moviesapp.service;

import org.example.moviesapp.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> listAllMovies();

    Movie getById(Long id);

    Movie editMovie(Long id, String title, String summary, String director);

    Movie deleteMovie(Long id);

    Movie saveMovie(String title, String summary, String director);
}
