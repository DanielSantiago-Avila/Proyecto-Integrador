package com.ProyectoIntegrador.ProyectoIntegrador.repository;

import com.ProyectoIntegrador.ProyectoIntegrador.entity.UserMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMongoRepository extends MongoRepository<UserMongoEntity, String> {


}
