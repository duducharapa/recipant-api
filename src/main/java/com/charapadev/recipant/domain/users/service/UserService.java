package com.charapadev.recipant.domain.users.service;

import com.charapadev.recipant.domain.recipes.dto.UpdateRecipeDTO;
import com.charapadev.recipant.domain.recipes.entity.Recipe;
import com.charapadev.recipant.domain.users.dto.CreateUserDTO;
import com.charapadev.recipant.domain.users.dto.UpdateUserDTO;
import com.charapadev.recipant.domain.users.entity.User;
import com.charapadev.recipant.domain.users.repository.UserRepository;
import com.charapadev.recipant.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j(topic = "User service")
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private void existsOrFail(UUID userId) throws ResourceNotFoundException {
        boolean userNotExists = !userRepository.existsById(userId);

        if (userNotExists) {
            log.error("Cannot found an user with ID: {}", userId);
            throw new ResourceNotFoundException("Cannot found an user with ID: " + userId);
        }
    }

    /**
     * List all users registered.
     *
     * @return The list of users found.
     */
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * Inserts a new user.
     *
     * @param createDTO The user creation information.
     * @return The user created.
     */

    public User create(CreateUserDTO createDTO) {
        User user = new User();

        user.setEmail(createDTO.email());
        user.setPassword(encoder.encode(createDTO.password()));
        user = userRepository.save(user);

        log.info("Created a new user: {}", user);
        return user;
    }

    /**
     * Tries to find a user on application using a given identifier.
     * Throws an error if not found anything.
     *
     * @param userId The user identifier.
     * @return The user found.
     * @throws ResourceNotFoundException When cannot find a user.
     */

    public User findOneOrFail(UUID userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
            .orElseThrow(() -> {
                log.error("Cannot found a user with ID: {}", userId);
                throw new ResourceNotFoundException("Cannot found a user with ID: " + userId);
            });
    }

    /**
     * Changes some information about a existent user.
     *
     * @param userId The user identifier.
     * @param updateDTO The information to override.
     * @throws ResourceNotFoundException When cannot find a user.
     */

    public void update(UUID userId, UpdateUserDTO updateDTO) throws ResourceNotFoundException {
        User user = findOneOrFail(userId);

        // TODO: Find a better logic to change only the not blank values without produce NullPointerException
        try {
            boolean emailNotBlank = !( updateDTO.email().isBlank() );
            if (emailNotBlank) {
                user.setEmail(updateDTO.email());
            }

            user = userRepository.save(user);
            log.info("Updated an user: {}", user);
        } catch (NullPointerException ignored) {}
    }

    public void delete(UUID userId) throws ResourceNotFoundException {
        existsOrFail(userId);

        userRepository.deleteById(userId);
        log.info("Removed an user with ID: {}", userId);
    }

}
