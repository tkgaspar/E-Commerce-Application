package com.example.demo.Controllers;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.controllers.OrderController;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.OrderRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private UserController userController;
    private OrderController orderController;
    private CartController cartController;

    private UserRepository userRepo=mock(UserRepository.class);
    private OrderRepository orderRepo=mock(OrderRepository.class);
    private CartRepository cartRepo=mock(CartRepository.class);;

    private UserOrder order=new UserOrder();
    private User user=new User();
    private Cart cart=new Cart();
    private Item item=new Item();

    @Before
    public void setup(){
        orderController=new OrderController();
        TestUtils.injectObjects(orderController,"userRepository",userRepo);
        TestUtils.injectObjects(orderController,"orderRepository",orderRepo);
        user.setId(0);
        user.setUsername("test");
        user.setPassword("password");
        user.setCart(cart);
        item.setName("stove");
        item.setId(0L);
        item.setPrice(BigDecimal.valueOf(14.99));
        cart.addItem(item);
        when(userRepo.findById(0L)).thenReturn(java.util.Optional.of(user));
        when(userRepo.findByUsername("test")).thenReturn(user);

    }


    @Test
    public void verify_submit(){
        OrderRequest request=new OrderRequest();
        request.setUser(user);
        request.setItems(cart.getItems());
        request.setTotal(cart.getTotal());
        ResponseEntity<UserOrder> response=orderController.submit(user.getUsername());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(BigDecimal.valueOf(14.99),response.getBody().getTotal());

    }

    @Test
    public void verify_GetOrdersForUser(){
        OrderRequest request=new OrderRequest();
        request.setUser(user);
        request.setItems(cart.getItems());
        request.setTotal(cart.getTotal());
        ResponseEntity<List<UserOrder>>response=orderController.getOrdersForUser(user.getUsername());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        user.setUsername("Gaspar");
        request.setUser(user);
        ResponseEntity<List<UserOrder>>response2=orderController.getOrdersForUser("Gaspar");
        assertEquals(404,response2.getStatusCodeValue());
    }

}
