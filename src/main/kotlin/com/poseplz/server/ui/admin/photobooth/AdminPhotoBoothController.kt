package com.poseplz.server.ui.admin.photobooth

import com.poseplz.server.application.photobooth.PhotoBoothApplicationService
import com.poseplz.server.application.photobooth.brand.BrandApplicationService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/photo-booth")
@Controller
class AdminPhotoBoothController(
    private val photoBoothApplicationService: PhotoBoothApplicationService,
    private val brandApplicationService: BrandApplicationService,
) {
    @GetMapping
    fun list(
        pageable: Pageable,
        model: Model,
    ): String {
        model.addAttribute("brandWithCountList", brandApplicationService.getBrandsWithCount())
        model.addAttribute("photoBoothPage", photoBoothApplicationService.getPhotoBooths(pageable))
        return "photobooth/list"
    }

    @PostMapping
    fun search(
        photoBoothSearchRequest: PhotoBoothSearchRequest,
        pageable: Pageable,
        model: Model,
    ): String {
        model.addAttribute("brandWithCountList", brandApplicationService.getBrandsWithCount())
        model.addAttribute("photoBoothPage", photoBoothApplicationService.getPhotoBooths(pageable))
        return "photobooth/list"
    }

    @GetMapping("/{photoBoothId}")
    fun detail(
        @PathVariable photoBoothId: Long,
    ): String {
        return "photobooth/detail"
    }

    @GetMapping("/add")
    fun addPage(
        model: Model,
    ): String {
        model.addAttribute("brandWithCountList", brandApplicationService.getBrandsWithCount())
        return "photobooth/add"
    }

    @PostMapping("/add")
    fun add(
        photoBoothAddRequest: PhotoBoothAddRequest,
    ): String {
        photoBoothApplicationService.addPhotoBooth(photoBoothAddRequest)
        return "redirect:/photo-booth"
    }

    @GetMapping("/{photoBoothId}/edit")
    fun editPage(
        @PathVariable photoBoothId: Long,
        model: Model,
    ): String {
        model.addAttribute("photoBooth", photoBoothApplicationService.getPhotoBooth(photoBoothId))
        return "photobooth/edit"
    }

    @PostMapping("/{photoBoothId}/edit")
    fun edit(
        @PathVariable photoBoothId: Long,
        photoBoothEditRequest: PhotoBoothEditRequest,
        model: Model,
    ): String {
        photoBoothApplicationService.editPhotoBooth(photoBoothId, photoBoothEditRequest)
        return "photobooth/detail"
    }

}
