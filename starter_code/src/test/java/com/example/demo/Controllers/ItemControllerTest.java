package com.example.demo.Controllers;

import com.example.demo.TestUtils;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.requests.ItemRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private Item item = new Item();
    private ItemController itemController;

    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);
        item.setId(1L);
        item.setName("Micimacko");
        item.setPrice(BigDecimal.valueOf(5000000));
        item.setDescription("vilag legdragabb mackoja, aranyos");
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepo.findByName("Micimacko")).thenReturn(itemList);

    }

    @Test
    public void verify_getItemById() {
        ItemRequest request = new ItemRequest();
        request.setId(1L);
        request.setName(item.getName());
        request.setPrice(item.getPrice());
        request.setDescription(item.getDescription());
        ResponseEntity<Item> returnedItem = itemController.getItemById(request.getId());
        assertNotNull(returnedItem);
        assertEquals(200, returnedItem.getStatusCodeValue());
        System.out.println(returnedItem.getBody().getDescription());
        assertEquals("Micimacko", returnedItem.getBody().getName());
        request.setId(2L);
        ResponseEntity<Item> secondReturnedItem = itemController.getItemById(request.getId());
        assertEquals(404, secondReturnedItem.getStatusCodeValue());
    }

    @Test
    public void getItemByName() {
        ItemRequest request = new ItemRequest();
        request.setId(1L);
        request.setName(item.getName());
        request.setPrice(item.getPrice());
        request.setDescription(item.getDescription());
        ResponseEntity<List<Item>> returnedItem = itemController.getItemsByName(request.getName());
        assertNotNull(returnedItem);
        assertEquals(200, returnedItem.getStatusCodeValue());
        assertEquals("Micimacko", returnedItem.getBody().get(0).getName());
    }


}
