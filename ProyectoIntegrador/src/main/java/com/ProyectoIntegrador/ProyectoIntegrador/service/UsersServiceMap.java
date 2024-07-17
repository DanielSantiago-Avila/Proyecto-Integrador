package com.ProyectoIntegrador.ProyectoIntegrador.service;

import com.ProyectoIntegrador.ProyectoIntegrador.dto.UserDto;
import com.ProyectoIntegrador.ProyectoIntegrador.entity.UserMongoEntity;
import com.ProyectoIntegrador.ProyectoIntegrador.exception.UserNotFoundException;
import com.ProyectoIntegrador.ProyectoIntegrador.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersServiceMap implements UsersService {

    @Autowired
    private UserMongoRepository userMongoRepository;

    @Override
    public List<UserDto> all() {
        return this.userMongoRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Optional<UserDto> findById(String id) {
        return this.userMongoRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    public UserDto save(UserDto user) {
        UserMongoEntity entity = new UserMongoEntity();
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        UserMongoEntity entitySaved = this.userMongoRepository.save(entity);
        return this.toDto(entitySaved);
    }

    @Override
    public UserDto update(UserDto user, String id) {
        UserMongoEntity entity = this.userMongoRepository.findById(id)
                .orElse(null);
        if (entity != null) {
            entity.setName(user.getName());
            entity.setEmail(user.getEmail());
            UserMongoEntity entitySaved = this.userMongoRepository.save(entity);
            return this.toDto(entitySaved);
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        UserMongoEntity entity = this.userMongoRepository.findById(id)
                .orElse(null);
        if (entity != null) {
            this.userMongoRepository.delete(entity);
        }
    }

    private UserDto toDto(UserMongoEntity entity) {
        return new UserDto(entity.getId(), entity.getName(), entity.getEmail());
    }
}
