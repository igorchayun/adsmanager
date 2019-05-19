package testtask.adsmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import testtask.adsmanager.domain.Banner;
import testtask.adsmanager.domain.Category;
import testtask.adsmanager.service.CategoryService;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        model.addAttribute("categories", categoryService.getAll(filter));
        model.addAttribute("filter", filter);
        return "categories";
    }

    @GetMapping("/new")
    public String viewCreateForm(Category category) {
        return "category";
    }

    @PostMapping
    public String create(@Valid Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "category";
        }
        if (categoryService.isNameExist(category, true)) {
            model.addAttribute(
                    "nameError",
                    "Category with name '" + category.getName() + "' is already exist"
            );
            return "category";
        }
        if (categoryService.isRequestExist(category, true)) {
            model.addAttribute(
                    "requestError",
                    "Category with request '" + category.getRequest() + "' is already exist"
            );
            return "category";
        }
        categoryService.create(category);
        return "redirect:/categories";
    }

    @GetMapping("{id}")
    public String viewEditForm(@PathVariable("id") Category category, Model model) {
        model.addAttribute("category", categoryService.getOne(category.getId()));
        return "category";
    }

    @PostMapping("{id}")
    public String update(
            @PathVariable("id") Category categoryFromDb,
            @Valid Category category,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "category";
        }
        if (categoryService.isNameExist(category, false)) {
            model.addAttribute(
                    "nameError",
                    "Category with name '" + category.getName() + "' is already exist"
            );
            return "category";
        }
        if (categoryService.isRequestExist(category, false)) {
            model.addAttribute(
                    "requestError",
                    "Category with request '" + category.getRequest() + "' is already exist"
            );
            return "category";
        }
        categoryService.update(category, categoryFromDb);
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Category category, Model model) {
        List<Banner> banners = categoryService.delete(category);
        if (banners != null) {
            model.addAttribute("category", category);
            model.addAttribute("banners", banners);
            return "associatedBanners";
        } else {
            return "redirect:/categories";
        }
    }
}
