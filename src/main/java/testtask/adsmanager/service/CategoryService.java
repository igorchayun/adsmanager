package testtask.adsmanager.service;

import org.springframework.beans.BeanUtils;
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

    public boolean isNameExist (Category category, boolean isNew) {
        Category categoryFromDb;
        if (isNew) {
            categoryFromDb = categoryRepository.findByNameAndDeletedFalse(category.getName());
        } else {
            categoryFromDb = categoryRepository.
                    findByNameAndIdNotAndDeletedFalse(category.getName(), category.getId());
        }
        return categoryFromDb != null;
    }

    public boolean isRequestExist (Category category, boolean isNew) {
        Category categoryFromDb;
        if (isNew) {
            categoryFromDb = categoryRepository.findByRequestAndDeletedFalse(category.getRequest());
        } else {
            categoryFromDb = categoryRepository.
                    findByRequestAndIdNotAndDeletedFalse(category.getRequest(), category.getId());
        }
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
        if (isNameExist(category, true) || isRequestExist(category, true)) {
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

    public Category update(Category category, Category categoryFromDb) {
        if (isNameExist(category, false) || isRequestExist(category, false)) {
            return null;
        }
        BeanUtils.copyProperties(category, categoryFromDb, "id");
        return categoryRepository.save(categoryFromDb);
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
