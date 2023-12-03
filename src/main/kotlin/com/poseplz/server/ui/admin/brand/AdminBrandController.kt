package com.poseplz.server.ui.admin.brand

import com.poseplz.server.application.photobooth.brand.BrandApplicationService
import com.poseplz.server.ui.admin.photobooth.PhotoBoothAddRequest
import com.poseplz.server.ui.admin.photobooth.PhotoBoothEditRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/brand")
@Controller
class AdminBrandController(
    private val brandApplicationService: BrandApplicationService,
) {
    @GetMapping
    fun list(
        pageable: Pageable,
        model: Model,
    ): String {
        model.addAttribute("brandPage", brandApplicationService.getBrands(pageable))
        return "brand/list"
    }

    @GetMapping("/{brandId}")
    fun detail(
        @PathVariable brandId: Long,
        model: Model,
    ): String {
        model.addAttribute("brand", brandApplicationService.getBrand(brandId))
        return "brand/detail"
    }

    @GetMapping("/add")
    fun addPage(): String {
        return "brand/add"
    }

    @PostMapping("/add")
    fun add(
        photoBoothAddRequest: PhotoBoothAddRequest,
    ): String {
        return "brand/list"
    }

    @GetMapping("/{brandId}/edit")
    fun editPage(
        @PathVariable brandId: Long,
        model: Model,
    ): String {
        model.addAttribute("brand", brandApplicationService.getBrand(brandId))
        return "brand/edit"
    }

    @PostMapping("/{brandId}/edit")
    fun edit(
        @PathVariable brandId: Long,
        photoBoothEditRequest: PhotoBoothEditRequest,
        model: Model,
    ): String {
        model.addAttribute("brand", brandApplicationService.getBrand(brandId))
        return "brand/detail"
    }
}
