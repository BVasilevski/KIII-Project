package org.example.moviesapp.service.impl;

import org.example.moviesapp.exception.MovieNotFoundException;
import org.example.moviesapp.model.Movie;
import org.example.moviesapp.repository.MovieRepository;
import org.example.moviesapp.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> listAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("The movie is not found"));
    }

    @Override
    public Movie editMovie(Long id, String title, String summary, String director) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("The movie is not found"));
        movie.setTitle(title);
        movie.setSummary(summary);
        movie.setDirector(director);
        return movieRepository.save(movie);
    }

    @Override
    public Movie deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("The movie is not found"));
        movieRepository.delete(movie);
        return movie;
    }

    @Override
    public Movie saveMovie(String title, String summary, String director) {
        Movie movie = new Movie(title, summary, director);
        return movieRepository.save(movie);
    }
}
