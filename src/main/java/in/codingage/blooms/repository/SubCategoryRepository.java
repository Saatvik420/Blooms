package in.codingage.blooms.repository;

import in.codingage.blooms.models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, String> {

    List<SubCategory> findAllByCategoryIdAndActiveTrue(String categoryId);
}
