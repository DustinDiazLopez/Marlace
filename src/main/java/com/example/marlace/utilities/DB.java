package com.example.marlace.utilities;

import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * {@link Database} wrapper
 */
public class DB {

    private final SessionFactory sessionFactory;

    public DB(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @see Database#save(SessionFactory, Object...)
     */
    public List<Serializable> save(final Object... objects) {
        return Database.save(this.sessionFactory, objects);
    }

    /**
     * @see Database#saveOrUpdate(SessionFactory, Object...)
     */
    public boolean saveOrUpdate(final Object... objects) {
        return Database.saveOrUpdate(this.sessionFactory, objects);
    }

    /**
     * @see Database#persist(SessionFactory, Object...)
     */
    public boolean persist(final Object... objects) {
        return Database.persist(this.sessionFactory, objects);
    }
}
