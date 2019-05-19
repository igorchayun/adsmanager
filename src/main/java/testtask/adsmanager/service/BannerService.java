package testtask.adsmanager.service;

import org.springframework.beans.BeanUtils;
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

    public boolean isNameExist(Banner banner, boolean isNew) {
        Banner bannerFromDb;
        if (isNew) {
            bannerFromDb = bannerRepository.findByNameAndDeletedFalse(banner.getName());
        } else {
            bannerFromDb = bannerRepository.findByNameAndIdNotAndDeletedFalse(banner.getName(), banner.getId());
        }
        return bannerFromDb != null;
    }

    public List<Banner> getAll(String filter) {
        if (!StringUtils.isEmpty(filter)) {
            return bannerRepository.findByNameContainingIgnoreCaseAndDeletedFalse(filter);
        } else {
            return bannerRepository.findByDeletedFalse();
        }
    }

    public Banner create(Banner banner) {
        if (isNameExist(banner, true)) {
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

    public Banner update(Banner banner, Banner bannerFromDb) {
        if (isNameExist(banner, false)) {
            return null;
        }
        BeanUtils.copyProperties(banner, bannerFromDb, "id");
        return bannerRepository.save(bannerFromDb);
    }

    public void delete(Banner banner) {
        banner.setDeleted(true);
        bannerRepository.save(banner);
    }
}
