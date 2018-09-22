package com.svlugovoy.hibernatedemo.dao.impl;


import com.svlugovoy.hibernatedemo.dao.ActorDao;
import com.svlugovoy.hibernatedemo.domain.Actor;
import com.svlugovoy.hibernatedemo.domain.Director;
import com.svlugovoy.hibernatedemo.exception.DaoOperationException;
import com.svlugovoy.hibernatedemo.util.EntityManagerUtil;
import org.hibernate.Session;
import org.hibernate.jpa.QueryHints;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ActorDaoImpl implements ActorDao {

    private EntityManagerFactory emf;
    private EntityManagerUtil emUtil;

    public ActorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
        this.emUtil = new EntityManagerUtil(emf);
    }

    @Override
    public Long save(Actor actor) {
        Objects.requireNonNull(actor);
        return emUtil.performReturningWithinTx(em -> {
            em.persist(actor);
            return actor.getId();
        });
    }

    @Override
    public List<Actor> findAll(boolean withMovieList) {
        if (!withMovieList) {
            return emUtil.performReturningWithinTx(em -> em.createQuery("select a from Actor a", Actor.class)
                    .setHint(QueryHints.HINT_READONLY, true)
                    .getResultList());
        } else {
            return emUtil.performReturningWithinTx(em -> em.createQuery("select a from Actor a join fetch a.movies", Actor.class)
                    .setHint(QueryHints.HINT_READONLY, true)
                    .getResultList());
        }
    }

    @Override
    public Actor findById(Long id) {
        Objects.requireNonNull(id);
        return emUtil.performReturningWithinTx(em -> {
            Session session = em.unwrap(Session.class);
            session.setDefaultReadOnly(true);
            Optional<Actor> actor = Optional.of(em.find(Actor.class, id));
            return actor.orElseThrow(() -> new DaoOperationException("Actor with id=" + id + " not exist."));
        });
    }

    @Override
    public void update(Actor actor) {
        Objects.requireNonNull(actor);
        emUtil.performWithinTx(em -> em.merge(actor));
    }

    @Override
    public void remove(Long id) {
        Objects.requireNonNull(id);
        emUtil.performWithinTx(em -> {
            Actor actor = em.find(Actor.class, id);
            em.remove(actor);
        });
    }

    @Override
    public List<Actor> findActorsInFilmsFromDirector(Director director) {
        Objects.requireNonNull(director);
        return emUtil.performReturningWithinTx(em -> {
            return em.createQuery("select a from Actor a inner join fetch a.movies m inner join m.director d where d.id = :id", Actor.class)
                    .setHint(QueryHints.HINT_READONLY, true)
                    .setParameter("id", director.getId())
                    .getResultList();
        });
    }

}
