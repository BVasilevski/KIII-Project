package org.example.moviesapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String title;

    public String summary;

    public String director;

    public Movie(String title, String summary, String director) {
        this.title = title;
        this.summary = summary;
        this.director = director;
    }
}
