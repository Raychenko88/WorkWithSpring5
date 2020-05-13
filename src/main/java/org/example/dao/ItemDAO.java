package org.example.dao;

import org.example.models.Cart;
import org.example.models.Item;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDAO extends BaseDAO<Item> {


    public List<Item> findByItemCode(String code) {
        Session session = hibernateSessionFactory.getSessionFactory().openSession();
        session.getTransaction().begin();
        String sql = "SELECT * FROM items WHERE code=:itemCodeParam";
        Query<Item> query = session.createNativeQuery(sql, Item.class);
        query.setParameter("itemCodeParam", code);
        List<Item> list = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List<Item> getAll() {
        Session session = hibernateSessionFactory.getSessionFactory().openSession();
        session.getTransaction().begin();
        String sql = "SELECT * FROM items";
        Query<Item> query = session.createNativeQuery(sql, Item.class);
        List<Item> list = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List<Item> getAllByCart(Cart cart) {
        Session session = hibernateSessionFactory.getSessionFactory().openSession();
        session.getTransaction().begin();
        String sql = "SELECT * FROM items i " +
                "JOIN orders o ON o.item_id = i.id " +
                "JOIN carts c ON c.id = o.cart_id " +
                "WHERE c.id =:cartId ";
        Query<Item> query = session.createNativeQuery(sql, Item.class);
        query.setParameter("cartId", cart.getId());
        List<Item> items = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    public List<Item> getAllAvialable() {
        Session session = hibernateSessionFactory.getSessionFactory().openSession();
        session.getTransaction().begin();
        String sql = "SELECT * FROM items WHERE availability > 0";
        Query<Item> query = session.createNativeQuery(sql, Item.class);
        List<Item> items = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return items;
    }

}
