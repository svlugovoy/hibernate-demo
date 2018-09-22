package com.svlugovoy.hibernatedemo.dao.impl;

import com.svlugovoy.hibernatedemo.dao.MovieDao;
import com.svlugovoy.hibernatedemo.domain.Actor;
import com.svlugovoy.hibernatedemo.domain.Movie;
import com.svlugovoy.hibernatedemo.domain.dto.MovieProjection;
import com.svlugovoy.hibernatedemo.util.EntityManagerUtil;
import org.hibernate.jpa.QueryHints;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Objects;

public class MovieDaoImpl implements MovieDao {

    private EntityManagerFactory emf;
    private EntityManagerUtil emUtil;

    public MovieDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
        this.emUtil = new EntityManagerUtil(emf);
    }

    @Override
    public List<Movie> findAllMoviesWithActor(Actor actor) {
        Objects.requireNonNull(actor);
        return emUtil.performReturningWithinTx(em -> {
            return em.createQuery("select m from Movie m inner join m.actors a where a.id = :id", Movie.class)
                    .setHint(QueryHints.HINT_READONLY, true)
                    .setParameter("id", actor.getId())
                    .getResultList();
        });
    }

    @Override
    public List<MovieProjection> findAllMoviesNamesWithActor(Actor actor) {
        Objects.requireNonNull(actor);
        return emUtil.performReturningWithinTx(em -> {
            return em.createQuery(
                    "select new com.svlugovoy.hibernatedemo.domain.dto.MovieProjection(m.id, m.name) from Movie m " +
                    " inner join m.actors a where a.id = :id", MovieProjection.class)
                    .setParameter("id", actor.getId())
                    .getResultList();
        });
    }
}

