package org.example.moviesapp.web;

import org.example.moviesapp.model.Movie;
import org.example.moviesapp.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/list")
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.listAllMovies();
        model.addAttribute("movies", movies);
        return "index";
    }

    @GetMapping("/add")
    public String getAddMoviePage(Model model) {
        return "add-movie";
    }

    @PostMapping("/add")
    public String addNewMovie(@RequestParam String title,
                              @RequestParam String summary,
                              @RequestParam String director) {
        movieService.saveMovie(title, summary, director);
        return "redirect:/movies/list";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable Long id,
                              Model model) {
        Movie movie = movieService.getById(id);
        model.addAttribute("movie", movie);
        return "edit-movie";
    }

    @PostMapping("/edit/{id}")
    public String editMovie(@PathVariable Long id,
                            @RequestParam String title,
                            @RequestParam String summary,
                            @RequestParam String director) {
        movieService.editMovie(id, title, summary, director);
        return "redirect:/movies/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies/list";
    }
}
