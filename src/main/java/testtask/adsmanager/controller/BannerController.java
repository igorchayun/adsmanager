package testtask.adsmanager.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import testtask.adsmanager.domain.Banner;
import testtask.adsmanager.service.BannerService;
import java.util.List;

@RestController
@RequestMapping("banner")
public class BannerController {
    private final BannerService bannerService;
    @Autowired
    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping
    public List<Banner> list(@RequestParam(required = false, defaultValue = "") String filter) {
        return bannerService.getAll(filter);
    }

    @PostMapping
    public Banner create(@RequestBody Banner banner) {
        return bannerService.create(banner);
    }

    @GetMapping("{id}")
    public Banner getOne(@PathVariable("id") Integer id) {
        return bannerService.getOne(id);
    }

    @PutMapping("{id}")
    public Banner update(@PathVariable("id") Banner bannerFromDb, @RequestBody Banner banner) {
        BeanUtils.copyProperties(banner, bannerFromDb, "id");
        return bannerService.update(bannerFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Banner banner) {
        bannerService.delete(banner);
    }
}
