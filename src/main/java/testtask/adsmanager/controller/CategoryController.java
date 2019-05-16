package testtask.adsmanager.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import testtask.adsmanager.domain.Category;
import testtask.adsmanager.service.CategoryService;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> list(@RequestParam(required = false, defaultValue = "") String filter) {
        return categoryService.getAll(filter);
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @GetMapping("{id}")
    public Category getOne(@PathVariable Integer id) {
        return categoryService.getOne(id);
    }

    @PutMapping("{id}")
    public Category update(@PathVariable("id") Category categoryFromDb, @RequestBody Category category) {
        BeanUtils.copyProperties(category, categoryFromDb, "id");
        return categoryService.update(categoryFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Category category) {
        categoryService.delete(category);
    }
}
