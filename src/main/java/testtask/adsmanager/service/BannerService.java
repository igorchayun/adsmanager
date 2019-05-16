package testtask.adsmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import testtask.adsmanager.domain.Banner;
import testtask.adsmanager.exceptions.NotFoundException;
import testtask.adsmanager.repository.BannerRepository;
import java.util.List;
import java.util.Optional;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;
    @Autowired
    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public List<Banner> getAll(String filter) {
        if (!StringUtils.isEmpty(filter)) {
            return bannerRepository.findByNameContainingIgnoreCaseAndDeletedFalse(filter);
        } else {
            return bannerRepository.findByDeletedFalse();
        }
    }

    public Banner create(Banner banner) {
        if (bannerRepository.findByNameAndDeletedFalse(banner.getName()) != null) {
            return null;
        }
        return bannerRepository.save(banner);
    }

    public Banner getOne(Integer id) {
        Optional<Banner> oBanner = bannerRepository.findByIdAndDeletedFalse(id);
        if (!oBanner.isPresent()) {
            throw new NotFoundException();
        }
        return oBanner.get();
    }

    public Banner update(Banner banner) {
        if (bannerRepository.findByNameAndIdNotAndDeletedFalse(banner.getName(), banner.getId()) != null) {
            return null;
        }
        return bannerRepository.save(banner);
    }

    public void delete(Banner banner) {
        banner.setDeleted(true);
        bannerRepository.save(banner);
    }
}
