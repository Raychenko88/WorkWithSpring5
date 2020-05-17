package org.example.services;

import org.example.dao.ItemDAO;
import org.example.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ItemServiceTest {
    private ItemService itemService = new ItemService();
    private ItemDAO itemDAO = Mockito.mock(ItemDAO.class);

    @BeforeEach
    public  void setBefore() {
        itemService.itemDAO = itemDAO;
    }


    @Test
    void createTest() {
        Item item = new Item("test_name", "test_code", 123, 1);
        when(itemDAO.save(any(Item.class))).thenReturn(item);
        Item item1 = itemService.create(item);
        assertNotNull(item1);
        assertEquals(item.getName(), item1.getName());
        verify(itemDAO,times(1)).save(any(Item.class));
    }

    @Test
    void updateTest() {
        Item item = new Item("test_name", "test_code", 123, 1);
        item.setId(1);
        when(itemDAO.findById(anyInt())).thenReturn(item);
        when(itemDAO.update(any(Item.class))).thenReturn(item);
        Item item1 = itemService.update(item);
        assertNotNull(item1);
        assertEquals(item.getName(), item1.getName());
    }
}