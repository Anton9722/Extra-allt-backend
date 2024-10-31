package com.extraAllt.extraAllt;

import com.extraAllt.extraAllt.models.User;
import com.extraAllt.extraAllt.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class UserTests {

    public List<String> list = new ArrayList<>();

    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mongoOperations.findOne(any(), eq(User.class))).thenReturn(null);
        when(mongoOperations.insert(any(User.class))).thenReturn(new User("1", "username", "password", false, 0, new ArrayList<>()));
    }
    
    @Test
    public void createUserTestError() {

        User newUser = new User("1", "username", "password", false, 0, list);
        when(mongoOperations.findOne(any(), eq(User.class))).thenThrow(new RuntimeException("Database error"));
        ResponseEntity<String> response = userService.createUser(newUser);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while creating the account", response.getBody());

    }

    @Test
    public void findUserUserExists() {

        User expectedUser = new User("1", "testUser", "password", false, 0, new ArrayList<>());
        when(mongoOperations.findOne(any(), eq(User.class))).thenReturn(expectedUser);
        User actualUser = userService.findUserByUsername("testUser");
        assertEquals(expectedUser, actualUser);
        
    }
}

