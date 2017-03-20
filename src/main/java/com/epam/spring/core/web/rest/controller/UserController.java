package com.epam.spring.core.web.rest.controller;

import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IUserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController("restUserController")
@RequestMapping("/api/user")
public class UserController {
    private static final Log LOGGER = LogFactory.getLog(UserController.class);

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
        LOGGER.info("Initializing is completed");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> user(@PathVariable Long id) {
        LOGGER.info("user GET request");
        LOGGER.info("id: " + id);

        User user = userService.getById(id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        user.setPassword("****************");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> all() {
        LOGGER.info("all GET request");

        Collection<User> users = userService.getAll();
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        users.forEach(user -> user.setPassword("****************"));
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody User user,
                                       UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.info("create POST request");

        boolean exists = userService.exists(user);
        if (exists)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        userService.save(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(user.getId()).toUri());

        return new ResponseEntity<>(user, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        LOGGER.info("update POST request");
        LOGGER.info("id: " + id);
        LOGGER.info("user: " + user);

        User persistedUser = userService.getById(id);
        if (persistedUser == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        persistedUser.setFirstName(user.getFirstName());
        persistedUser.setLastName(user.getLastName());
        persistedUser.setBirthday(user.getBirthday());
        persistedUser.setEmail(user.getEmail());

        userService.save(persistedUser);

        return new ResponseEntity<>(persistedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOGGER.info("delete DELETE request");
        LOGGER.info("id: " + id);

        User user = userService.getById(id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userService.remove(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
