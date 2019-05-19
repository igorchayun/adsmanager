package testtask.adsmanager.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import testtask.adsmanager.domain.Category;
import testtask.adsmanager.service.CategoryService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryRestController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryRestController(CategoryService categoryService) {
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
        return categoryService.update(category, categoryFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Category category) {
        categoryService.delete(category);
    }
}
