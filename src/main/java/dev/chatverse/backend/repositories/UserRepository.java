package dev.chatverse.backend.repositories;

import dev.chatverse.backend.documents.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}