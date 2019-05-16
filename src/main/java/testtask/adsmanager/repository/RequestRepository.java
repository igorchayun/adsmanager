package testtask.adsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import testtask.adsmanager.domain.Banner;
import testtask.adsmanager.domain.Request;
import java.time.LocalDateTime;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query("select r.banner from Request r " +
            "where r.userAgent = :userAg and r.ipAddress = :ipAddr " +
            "and r.date >= :currDate")
    List<Banner> bannersFromRequest(
            @Param("userAg") String userAg,
            @Param("ipAddr") String ipAddr,
            @Param("currDate") LocalDateTime currDate);
}
