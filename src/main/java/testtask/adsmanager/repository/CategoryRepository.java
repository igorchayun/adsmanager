package testtask.adsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testtask.adsmanager.domain.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByNameAndDeletedFalse(String name);

    Category findByRequestAndDeletedFalse(String request);

    List<Category> findByDeletedFalse();

    Optional<Category> findByIdAndDeletedFalse(Integer id);

    Category findByNameAndIdNotAndDeletedFalse(String name, Integer id);

    Category findByRequestAndIdNotAndDeletedFalse(String request, Integer id);

    List<Category> findByNameContainingIgnoreCaseAndDeletedFalse(String filter);
}

