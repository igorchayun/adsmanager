package testtask.adsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testtask.adsmanager.domain.Banner;
import testtask.adsmanager.domain.Category;
import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

    Banner findByNameAndDeletedFalse(String name);

    List<Banner> findByCategoryAndDeletedFalse(Category category);

    List<Banner> findByDeletedFalse();

    List<Banner> findByNameContainingIgnoreCaseAndDeletedFalse(String filter);

    Optional<Banner> findByIdAndDeletedFalse(Integer id);

    Banner findByNameAndIdNotAndDeletedFalse(String name, Integer id);
}
