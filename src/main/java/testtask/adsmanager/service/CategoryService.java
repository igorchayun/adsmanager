package testtask.adsmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import testtask.adsmanager.domain.Banner;
import testtask.adsmanager.domain.Category;
import testtask.adsmanager.exceptions.NotFoundException;
import testtask.adsmanager.repository.BannerRepository;
import testtask.adsmanager.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BannerRepository bannerRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, BannerRepository bannerRepository) {
        this.categoryRepository = categoryRepository;
        this.bannerRepository = bannerRepository;
    }

    private boolean isNameExist (String name) {
        Category categoryFromDb = categoryRepository.findByNameAndDeletedFalse(name);
        return categoryFromDb != null;
    }

    private boolean isRequestExist (String request) {
        Category categoryFromDb = categoryRepository.findByRequestAndDeletedFalse(request);
        return categoryFromDb != null;
    }

    public List<Category> getAll(String filter) {
        if (!StringUtils.isEmpty(filter)) {
            return categoryRepository.findByNameContainingIgnoreCaseAndDeletedFalse(filter);
        } else {
            return categoryRepository.findByDeletedFalse();
        }
    }

    public Category create(Category category) {
        if (isNameExist(category.getName()) || isRequestExist(category.getRequest())) {
            return null;
        }
        return categoryRepository.save(category);
    }

    public Category getOne(Integer id) {
        Optional<Category> oCategory = categoryRepository.findByIdAndDeletedFalse(id);
        if (!oCategory.isPresent()) {
            throw new NotFoundException();
        }
        return oCategory.get();
    }

    public Category update(Category category) {
        if (categoryRepository.findByNameAndIdNotAndDeletedFalse(category.getName(), category.getId()) != null) {
            return null;
        }
        if (categoryRepository.findByRequestAndIdNotAndDeletedFalse(category.getRequest(), category.getId()) != null) {
            return null;
        }
        return categoryRepository.save(category);
    }

    public List<Banner> delete(Category category) {
        List<Banner> banners = bannerRepository.findByCategoryAndDeletedFalse(category);
        if (banners.isEmpty()) {
            category.setDeleted(true);
            categoryRepository.save(category);
            return null;
        }
        return banners;
    }
}
