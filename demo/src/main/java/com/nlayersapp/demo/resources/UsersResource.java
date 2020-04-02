package com.nlayersapp.demo.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import com.nlayersapp.demo.Models.*;

@RestController()
@RequestMapping("/api/users")
public class UsersResource {

    User[] users;
    public UsersResource() {
        User user1 = new User("user1", 5000L); 
        User user2 = new User("user2", 5000L);
        users = new User[] { user1, user2 };
    }

    @GetMapping("/all")
    public List<User> GetAll() {
        return Arrays.asList(users);
    }

    @GetMapping(value= "/hateaos/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<User> GetHATEOAS() {
        Link link = ControllerLinkBuilder.linkTo(UsersResource.class).slash(users[0].getName()).withSelfRel();
        users[0].add(link);
        
        link = ControllerLinkBuilder.linkTo(UsersResource.class).slash(users[1].getName()).withSelfRel();
        users[1].add(link);
        
        return Arrays.asList(users);
    }    
}
