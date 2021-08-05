package com.example.demo.Controllers;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {


        private UserController userController;
        private CartController cartController;

        private UserRepository userRepo = mock(UserRepository.class);
        private CartRepository cartRepo = mock(CartRepository.class);
        private ItemRepository itemRepo = mock(ItemRepository.class);
        private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

        private Cart cart = new Cart();
        private User user = new User();
        private Item item = new Item();

        @Before
        public void setup() {
            cartController = new CartController();
            TestUtils.injectObjects(cartController, "userRepository", userRepo);
            TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
            TestUtils.injectObjects(cartController, "itemRepository", itemRepo);

            user.setId(0);
            user.setUsername("test");
            user.setPassword("password");
            user.setCart(cart);
            item.setName("stove");
            item.setId(0L);
            item.setPrice(BigDecimal.valueOf(14.99));
            when(userRepo.findById(0L)).thenReturn(java.util.Optional.of(user));
            when(userRepo.findByUsername("test")).thenReturn(user);
            when(itemRepo.findById(0L)).thenReturn(Optional.of(item));
        }



        @Test
        public void verify_addToCart() {
            ModifyCartRequest request = new ModifyCartRequest();
            request.setItemId(0L);
            request.setUsername(user.getUsername());
            request.setQuantity(2);
            ResponseEntity<Cart> response = cartController.addTocart(request);
            assertNotNull(response);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals(BigDecimal.valueOf(29.98),response.getBody().getTotal());
            request.setItemId(2);
            request.setUsername("Gaspar");
            response = cartController.addTocart(request);
            assertEquals(404,response.getStatusCodeValue());
        }

        @Test
        public void verify_removeFromCart(){
            ModifyCartRequest request = new ModifyCartRequest();
            request.setItemId(0L);
            request.setUsername(user.getUsername());
            request.setQuantity(1);
            ResponseEntity<Cart> response = cartController.removeFromcart(request);
            assertNotNull(response);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals(BigDecimal.valueOf(-14.99),response.getBody().getTotal());
            request.setItemId(2);
            request.setUsername("Gaspar");
            response = cartController.addTocart(request);
            assertEquals(404,response.getStatusCodeValue());

        }


}
