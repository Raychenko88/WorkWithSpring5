
package org.example.factory;

import org.example.config.SpringConfig;
import org.example.factory.impl.H2Factory;
import org.example.factory.impl.PostgresFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(SpringConfig.class)
@ActiveProfiles("test")
class HibernateSessionFactoryTest {
    @Autowired
    HibernateSessionFactory hibernateSessionFactory;

    @Test
    void getSessionFactory() {
        assertTrue(hibernateSessionFactory instanceof H2Factory);
        SessionFactory sessionFactory = hibernateSessionFactory.getSessionFactory();
        assertNotNull(sessionFactory);

        Session session = sessionFactory.openSession();

        assertNotNull(session);

        session.close();
    }
}