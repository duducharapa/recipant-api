package com.charapadev.recipant.domain.users.controller;

import com.charapadev.recipant.domain.recipes.dto.ShowRecipeDTO;
import com.charapadev.recipant.domain.users.dto.CreateUserDTO;
import com.charapadev.recipant.domain.users.dto.UpdateUserDTO;
import com.charapadev.recipant.domain.users.entity.User;
import com.charapadev.recipant.domain.users.service.UserService;
import com.charapadev.recipant.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Service used to manipulate the users instance.
 */

@RestController
@Api(value = "users")
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Shows all users")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of users found", response = User.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Error listing an users")
    })
    @GetMapping
    public ResponseEntity<List<User>> list() {
        List<User> users = userService.list();

        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "Creates a new user")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "User created", response = User.class),
        @ApiResponse(code = 500, message = "Error creating a new user")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserDTO createDTO) {
        User user = userService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @ApiOperation(value = "Searches an user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User found", response = User.class),
        @ApiResponse(code = 404, message = "Cannot found an user"),
        @ApiResponse(code = 500, message = "Error searching an user")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<User> find(@PathVariable("userId") UUID userId)
        throws ResourceNotFoundException
    {
        User user = userService.findOneOrFail(userId);
        
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Updates an user")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "User updated"),
        @ApiResponse(code = 404, message = "Cannot found an user"),
        @ApiResponse(code = 500, message = "Error updating an user")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{userId}")
    public ResponseEntity<Void> update(
        @PathVariable("userId") UUID userId,
        @Valid @RequestBody UpdateUserDTO updateDTO
    ) throws ResourceNotFoundException {
        userService.update(userId, updateDTO);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Removes an user")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "User removed"),
        @ApiResponse(code = 404, message = "Cannot found an user"),
        @ApiResponse(code = 500, message = "Error removing an user")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId) throws ResourceNotFoundException {
        userService.delete(userId);

        return ResponseEntity.noContent().build();
    }

}
