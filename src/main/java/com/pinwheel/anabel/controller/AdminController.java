package com.pinwheel.anabel.controller;

import com.pinwheel.anabel.entity.SiteSetting;
import com.pinwheel.anabel.entity.dto.JsonResponse;
import com.pinwheel.anabel.entity.dto.SiteSettingDto;
import com.pinwheel.anabel.service.SiteSettingService;
import com.pinwheel.anabel.service.notification.FlushNotificationMessageFactory;
import com.pinwheel.anabel.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Admin controller.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    /**
     * Injection of {@link ModelMapper} bean.
     */
    private final ModelMapper modelMapper;

    /**
     * Injection of {@link NotificationService} bean.
     */
    private final NotificationService notificationService;

    /**
     * Inject of {@link FlushNotificationMessageFactory} bean.
     */
    private final FlushNotificationMessageFactory flushNotificationMessageFactory;

    private final SiteSettingService siteSettingService;

    /**
     * Returns admin page.
     *
     * @return admin page path.
     */
    @GetMapping
    public String admin(Model model) {
        return "admin/main";
    }

    @GetMapping("/settings")
    public String siteSettings(
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<SiteSetting> page = siteSettingService.findAll(pageable);

        model.addAttribute("siteSettings", siteSettingService);
        model.addAttribute("page", page);

        return "admin/settings";
    }

    @PostMapping(value = "/settings", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<JsonResponse<SiteSettingDto>> siteSettings(
            @Valid SiteSettingDto siteSettingDto,
            BindingResult bindingResult,
            Model model
    ) {
        if(bindingResult.hasErrors()){
            return ResponseEntity
                    .unprocessableEntity()
                    .body(new JsonResponse<>(
                            siteSettingDto,
                            bindingResult.getFieldErrors()
                    ));
        } else {
            return ResponseEntity
                    .ok()
                    .body(new JsonResponse<>(siteSettingDto));
        }
    }
}
