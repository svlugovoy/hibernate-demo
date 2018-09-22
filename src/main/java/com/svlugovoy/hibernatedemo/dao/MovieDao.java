package com.svlugovoy.hibernatedemo.dao;

import com.svlugovoy.hibernatedemo.domain.Actor;
import com.svlugovoy.hibernatedemo.domain.Movie;
import com.svlugovoy.hibernatedemo.domain.dto.MovieProjection;

import java.util.List;

public interface MovieDao {

    List<Movie> findAllMoviesWithActor(Actor actor);

    List<MovieProjection> findAllMoviesNamesWithActor(Actor actor);
}
