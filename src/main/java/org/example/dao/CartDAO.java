package org.example.dao;

import org.example.models.Cart;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class CartDAO extends BaseDAO<Cart>{

    public List<Cart> getAllByUserAndPeriod(User user, Long timeFrom, Long timeTo){
        Session session = hibernateSessionFactory.getSessionFactory().openSession();
        session.getTransaction().begin();
        String sql = "SELECT * FROM carts WHERE user_id=:userId AND creation_time >=:timeDown AND creation_time <=:timUp";
        Query<Cart> query = session.createNativeQuery(sql, Cart.class);
        query.setParameter("userId", user.getId());
        query.setParameter("timeDown", timeFrom);
        query.setParameter("timUp", timeTo);
        List<Cart> list = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public  Cart getByUserAndOpenStatus(User user){
        Session session = hibernateSessionFactory.getSessionFactory().openSession();
        session.getTransaction().begin();
        String sql = "SELECT * FROM carts WHERE user_id =:id AND closed='0'";
        Query<Cart> query = session.createNativeQuery(sql, Cart.class);
        query.setParameter("id", user.getId());
        Cart cart = query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return cart;
    }

    public Cart updateStatus(Cart cart, Integer closed){
        Session session = hibernateSessionFactory.getSessionFactory().openSession();
        session.getTransaction().begin();
        String sql = "UPDATE carts SET closed=:closedParam WHERE id =:idParam";
        Query query = session.createNativeQuery(sql, Cart.class);
        query.setParameter("closedParam", closed);
        query.setParameter("idParam", cart.getId());
        query.executeUpdate();
        session.getTransaction().commit();
        cart.setClosed(closed);
        session.close();
        return cart;
    }
}
