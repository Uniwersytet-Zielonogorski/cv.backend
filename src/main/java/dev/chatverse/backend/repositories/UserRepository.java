package dev.chatverse.backend.repositories;

import dev.chatverse.backend.documents.User.Role;
import dev.chatverse.backend.documents.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
    Optional<Set<User>> findByRoles(Set<Role> roles);
    Optional<User> findByGivenNameAndFamilyName(String givenName, String familyName);
}