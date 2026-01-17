package in.codingage.blooms.repository;

import in.codingage.blooms.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    // Custom query method to find all active categories
    List<Category> findAllByActiveTrue();
//    List<Category> findAllByName();

    Optional<Category> findByName(String name);
}
