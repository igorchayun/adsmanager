package testtask.adsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testtask.adsmanager.domain.Banner;
import testtask.adsmanager.domain.Category;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

    Banner findByNameAndDeletedFalse(String name);

    List<Banner> findByCategoryAndDeletedFalse(Category category);
}
