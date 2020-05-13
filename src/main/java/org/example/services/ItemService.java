package org.example.services;

import org.example.dao.ItemDAO;
import org.example.dao.UserDAO;
import org.example.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemDAO itemDAO;

    public Item create(Item item) {
        return itemDAO.save(item);
    }

    public Item findById(Integer id) {
        return itemDAO.findById(id);
    }
}
