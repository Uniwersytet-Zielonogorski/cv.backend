package dev.chatverse.backend.repositories;

import dev.chatverse.backend.documents.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);

}