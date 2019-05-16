package testtask.adsmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import testtask.adsmanager.domain.Banner;
import testtask.adsmanager.domain.Category;
import testtask.adsmanager.domain.Request;
import testtask.adsmanager.repository.BannerRepository;
import testtask.adsmanager.repository.CategoryRepository;
import testtask.adsmanager.repository.RequestRepository;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("bid")
public class RequestController {
    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;
    private final BannerRepository bannerRepository;
    @Autowired
    public RequestController(RequestRepository requestRepository,
                             CategoryRepository categoryRepository,
                             BannerRepository bannerRepository
    ) {
        this.requestRepository = requestRepository;
        this.categoryRepository = categoryRepository;
        this.bannerRepository = bannerRepository;
    }
    @GetMapping
    public Object getBanner(@RequestParam String category, HttpServletRequest request) {
        Category categoryObj = categoryRepository.findByRequestAndDeletedFalse(category);

        List<Banner> viewedBanners = requestRepository.bannersFromRequest(
                request.getHeader("User-Agent"),
                request.getRemoteAddr(),
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN));

        List<Banner> newBanners;
        Banner banner;
        if ( viewedBanners.size() > 0 ) {
            List<Integer> bannerIds = new ArrayList<>();
            for (Banner ban : viewedBanners) {
                bannerIds.add(ban.getId());
            }
            newBanners = bannerRepository.findToShow(bannerIds, categoryObj);
        } else {
            newBanners = bannerRepository.findToFirstShow(categoryObj);
        }
        if (!newBanners.isEmpty()) {
            banner = newBanners.get(0);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

        Request requestToDb = new Request(
                banner,
                request.getHeader("User-Agent"),
                request.getRemoteAddr(),
                LocalDateTime.now());
        requestRepository.save(requestToDb);

        return banner.getContent();
    }
}
