package com.example.demo.Controllers;

import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    private User user=new User();

    @Before

    public void setup() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);

        user.setId(0);
        user.setUsername("test");
        when(userRepo.findById(0L)).thenReturn(java.util.Optional.of(user));
        when(userRepo.findByUsername("test")).thenReturn(user);

    }

    @Test
    public void create_user_happy_path() {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("testPassword");
        request.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.createUser(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("thisIsHashed", user.getPassword());

    }

    @Test
    public void testFindByUsername() throws IOException {
        ResponseEntity<User> response2=userController.findByUserName("test");
        assertEquals( 200, response2.getStatusCodeValue());
        assertEquals("test",user.getUsername());
    }
    @Test
    public void testFindById() throws Exception{

        ResponseEntity<User> response=userController.findById(0L);
        assertEquals( 0, response.getBody().getId());
        assertEquals("test",user.getUsername());
        ResponseEntity<User> anotherResponse=userController.findById(1L);
        assertEquals(404,anotherResponse.getStatusCodeValue());
    }



}
