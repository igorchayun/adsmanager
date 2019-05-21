package testtask.adsmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import testtask.adsmanager.domain.Banner;
import testtask.adsmanager.service.BannerService;
import testtask.adsmanager.service.CategoryService;
import javax.validation.Valid;

@Controller
@RequestMapping("/banners")
public class BannerController {
    private final BannerService bannerService;
    private final CategoryService categoryService;
    @Autowired
    public BannerController(BannerService bannerService, CategoryService categoryService) {
        this.bannerService = bannerService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        model.addAttribute("banners", bannerService.getAll(filter));
        model.addAttribute("filter", filter);
        return "banners";
    }

    @GetMapping("/new")
    public String viewCreateForm(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        model.addAttribute("allCategories", categoryService.getAll(null));
        model.addAttribute("banner", new Banner());
        model.addAttribute("banners", bannerService.getAll(filter));
        model.addAttribute("filter", filter);
        return "banner";
    }

    @PostMapping
    public String create(@Valid Banner banner, BindingResult bindingResult, Model model) {
        model.addAttribute("allCategories", categoryService.getAll(null));
        model.addAttribute("banners", bannerService.getAll(null));
        if (bindingResult.hasErrors()) {
            return "banner";
        }
        if (bannerService.isNameExist(banner, true)) {
            model.addAttribute(
                    "nameError",
                    "Banner with name '" + banner.getName() + "' is already exist"
            );
            return "banner";
        }
        bannerService.create(banner);
        return "redirect:/banners";
    }

    @GetMapping("{id}")
    public String viewEditForm(
            @PathVariable("id") Banner banner,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        model.addAttribute("allCategories", categoryService.getAll(null));
        model.addAttribute("banner", bannerService.getOne(banner.getId()));
        model.addAttribute("banners", bannerService.getAll(filter));
        model.addAttribute("filter", filter);
        return "banner";
    }

    @PostMapping("{id}")
    public String update(
            @PathVariable("id") Banner bannerFromDb,
            @Valid Banner banner,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("allCategories", categoryService.getAll(null));
        model.addAttribute("banners", bannerService.getAll(null));
        if (bindingResult.hasErrors()) {
            return "banner";
        }
        if (bannerService.isNameExist(banner, false)) {
            model.addAttribute(
                    "nameError",
                    "Banner with name '" + banner.getName() + "' is already exist"
            );
            return "banner";
        }
        bannerService.update(banner, bannerFromDb);
        return "redirect:/banners";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Banner banner) {
        bannerService.delete(banner);
        return "redirect:/banners";
    }
}
