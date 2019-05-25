package testtask.adsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "select * from Banner " +
            "where category_id = :category and deleted = false " +
            "order by price desc limit 1", nativeQuery = true)
    List<Banner> findToFirstShow(@Param("category") Category category);

    @Query(value = "select * from Banner " +
            "where id not in :bannerIds and category_id = :category and deleted = false " +
            "order by price desc limit 1", nativeQuery = true)
    List<Banner> findToShow(@Param("bannerIds") List<Integer> bannerIds,
                            @Param("category") Category category);
}
