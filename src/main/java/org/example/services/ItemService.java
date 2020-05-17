package org.example.services;

import org.example.dao.ItemDAO;
import org.example.models.Cart;
import org.example.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    protected ItemDAO itemDAO;

    public Item create(Item item) {
        // TODO изменить на item code
//        if (itemDAO.findByItemCode(item.getCode()) == null){
//            return itemDAO.save(item);
//        }
        List<Item> list = itemDAO.findByItemCode(item.getCode());
        if (list.isEmpty()){
            return itemDAO.save(item);
        }
        return null;
    }

    public Item update(Item item){
        if (itemDAO.findById(item.getId()) != null){
            return itemDAO.update(item);
        }
        return null;
    }

    public Item findById(Integer id){
        return itemDAO.findById(id);
    }

    public void delete(Item item){
        if (itemDAO.findById(item.getId()) != null){
            itemDAO.delete(item);
        }
    }

    public List<Item> findByItemCode(String code){
        return itemDAO.findByItemCode(code);
    }

    public List<Item> getAll(){
        return itemDAO.getAll();
    }

    public List<Item> getAllByCart(Cart cart){
        return itemDAO.getAllByCart(cart);
    }

    public List<Item> getAllAvailability(){
        return itemDAO.getAllAvialable();
    }

}
