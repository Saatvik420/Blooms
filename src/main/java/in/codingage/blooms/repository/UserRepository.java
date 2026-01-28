package in.codingage.blooms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.codingage.blooms.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    User findByName(String name);
    
    User findByUsername(String username);

    User findByPhone(String phone);
}
