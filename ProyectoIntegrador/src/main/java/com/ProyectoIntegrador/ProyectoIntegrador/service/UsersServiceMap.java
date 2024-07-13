package com.ProyectoIntegrador.ProyectoIntegrador.service;

import com.ProyectoIntegrador.ProyectoIntegrador.exception.UserNotFoundException;
import com.ProyectoIntegrador.ProyectoIntegrador.repository.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersServiceMap implements UsersService {

    private final Map<String, User> usersMap = new HashMap<>();

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        usersMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(usersMap.get(id));
    }

    @Override
    public List<User> all() {
        return new ArrayList<>(usersMap.values());
    }

    @Override
    public void deleteById(String id) {
        usersMap.remove(id);
    }

    @Override
    public User update(User user, String userId) {
        if (!usersMap.containsKey(userId)) {
            throw new UserNotFoundException(userId);
        }
        user.setId(userId);  // Asegúrate de que el ID es correcto para la actualización
        return save(user);
    }
}
