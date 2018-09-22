package com.svlugovoy.hibernatedemo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"director", "actors"})
@Builder
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "year_of_creation", nullable = false)
    private Integer yearOfCreation;

    @Column(name = "genre", nullable = false)
    private String  genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "directors_id")
    private Director director;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY)
    private Set<Actor> actors = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
