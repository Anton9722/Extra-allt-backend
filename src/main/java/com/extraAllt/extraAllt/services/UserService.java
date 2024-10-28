package com.extraAllt.extraAllt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.extraAllt.extraAllt.models.LoginResponse;
import com.extraAllt.extraAllt.models.User;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //skapa nytt konto
    public ResponseEntity<String> createUser(User newUser) {
        try {
            String encodedPassword = passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(encodedPassword);

            String username = newUser.getUsername();
            Query query = new Query();
            query.addCriteria(Criteria.where("username").is(username));
    
            if(mongoOperations.findOne(query, User.class) == null) {
                mongoOperations.insert(newUser);
                return ResponseEntity.status(HttpStatus.CREATED).body("New account has been created");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("An account with that username already exist");
            }
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the account");
        }
    }

    // hämta användare från användarnamn
    public User findUserByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return mongoOperations.findOne(query, User.class);
    }
    
    //Verifiera inloggning
    public LoginResponse loginUser(String username, String password){
        try{
            User user = findUserByUsername(username);
            if(user == null) {
                return new LoginResponse(false, "We couldn't find an account with that username", null);
            } else {
                boolean isPasswordCorrect = passwordEncoder.matches(password, user.getPassword());
                if(isPasswordCorrect) {
                    return new LoginResponse(true, "Login Successful", user.getId());
                } else {
                    return new LoginResponse(false, "Password incorrect", null);
                }
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
            return new LoginResponse(false, "An error occurred while verifying login", null);
        }
    }

    // hämta alla users
    public List<User> getAllUsers() {
        return mongoOperations.findAll(User.class);
    }

    //updaterar user points
    public void updatePoints(int points, String userId){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(userId));
            User user = mongoOperations.findOne(query, User.class);
            
            int allPoints = user.getPoints() + points;

            Update update = Update.update("points", allPoints);
            mongoOperations.updateFirst(query, update, User.class);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
