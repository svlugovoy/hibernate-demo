package com.svlugovoy.hibernatedemo.dao.impl;

import com.svlugovoy.hibernatedemo.dao.DirectorDao;
import com.svlugovoy.hibernatedemo.domain.Director;
import com.svlugovoy.hibernatedemo.exception.DaoOperationException;
import com.svlugovoy.hibernatedemo.util.EntityManagerUtil;
import org.hibernate.Session;

import javax.persistence.EntityManagerFactory;
import java.util.Objects;
import java.util.Optional;

public class DirectorDaoImpl implements DirectorDao {

    private EntityManagerFactory emf;
    private EntityManagerUtil emUtil;

    public DirectorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
        this.emUtil = new EntityManagerUtil(emf);
    }

    @Override
    public Director findById(Long id) {
        Objects.requireNonNull(id);
        return emUtil.performReturningWithinTx(em -> {
            Session session = em.unwrap(Session.class);
            session.setDefaultReadOnly(true);
            Optional<Director> actor = Optional.of(em.find(Director.class, id));
            return actor.orElseThrow(() -> new DaoOperationException("Director with id=" + id + " not exist."));
        });
    }
}
