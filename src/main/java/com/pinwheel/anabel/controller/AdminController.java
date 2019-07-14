package com.pinwheel.anabel.controller;

import com.pinwheel.anabel.entity.SiteSetting;
import com.pinwheel.anabel.entity.SiteSettingType;
import com.pinwheel.anabel.entity.Status;
import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.dto.SiteSettingDto;
import com.pinwheel.anabel.service.SiteSettingService;
import com.pinwheel.anabel.service.notification.NotificationService;
import com.pinwheel.anabel.service.notification.domain.Notification;
import com.pinwheel.anabel.service.notification.factory.FlushNotificationMessageFactory;
import com.pinwheel.anabel.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Admin controller. Contains actions allowed for administrators only.
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

    /**
     * Inject of {@link SiteSettingService} bean.
     */
    private final SiteSettingService siteSettingService;

    /**
     * Returns admin page.
     *
     * @return admin page path.
     */
    @GetMapping
    public String admin() {
        return "admin/main";
    }

    /**
     * Returns settings. Separates site settings by pages.
     *
     * @param model
     * @param pageable paginator for settings.
     * @return settings list template.
     */
    @GetMapping("/settings")
    public String siteSettings(
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<SiteSetting> page = siteSettingService.findAll(pageable);

        model.addAttribute("page", page);
        model.addAttribute("url", "/admin/settings");

        return "admin/settings";
    }

    /**
     * Returns page of creating or updating site setting.
     *
     * @param siteSetting site setting for updating.
     * @param model
     * @return template for updating or creating site setting.
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @GetMapping("/settings/form")
    public String siteSettingEdit(
            @ModelAttribute("id") SiteSetting siteSetting,
            Model model)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SiteSettingDto siteSettingDto = siteSetting != null ? new SiteSettingDto(
                siteSetting.getId(),
                siteSetting.getKey(),
                siteSetting.getType(),
                siteSetting.getValue(),
                siteSetting.getStatus()
        ) : new SiteSettingDto();

        model.addAttribute("siteSettingDto", siteSettingDto);
        model.addAttribute("siteSettingTypeOptions", ConvertUtils.enumToOptions(SiteSettingType.class));
        model.addAttribute("siteSettingStatusOptions", ConvertUtils.enumValuesToOptions(
                List.of(Status.ACTIVE, Status.DISABLED)
        ));

        return "admin/site-setting-form";
    }

    /**
     * Action for creating new and updating exist settings. If request attributes contains id, system will find the
     * setting by this id and updates it.
     *
     * @param siteSettingDto     site setting DTO, which will be mapped to {@link SiteSetting} instance.
     * @param bindingResult
     * @param model
     * @param redirectAttributes
     * @return redirect to settings list page in success case or back to form in failure case.
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @PostMapping("/settings/form")
    public String siteSettingEdit(
            @Valid SiteSettingDto siteSettingDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("siteSettingDto", siteSettingDto);
            model.addAttribute("siteSettingTypeOptions", ConvertUtils.enumToOptions(SiteSettingType.class));
            model.addAttribute("siteSettingStatusOptions", ConvertUtils.enumValuesToOptions(
                    List.of(Status.ACTIVE, Status.DISABLED)
            ));

            return "admin/site-setting-form";
        } else {
            SiteSetting siteSetting = modelMapper.map(siteSettingDto, SiteSetting.class);

            siteSettingService.save(siteSetting);

            notificationService.send(Notification.builder()
                    .put(
                            "flush",
                            new User(),
                            flushNotificationMessageFactory.create(
                                    "success",
                                    redirectAttributes,
                                    "Setting has been saved successfully."
                            )
                    ).build());

            return "redirect:/admin/settings";
        }
    }

    /**
     * Deletes site setting by id.
     *
     * @param id                 id of site setting.
     * @param redirectAttributes
     * @return redirect to site settings list page.
     */
    @GetMapping("/settings/delete/{id}")
    public String deleteSetting(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes
    ) {
        siteSettingService.delete(id);

        notificationService.send(Notification.builder()
                .put(
                        "flush",
                        new User(),
                        flushNotificationMessageFactory.create(
                                "success",
                                redirectAttributes,
                                "Setting has been removed successfully."
                        )
                ).build());

        return "redirect:/admin/settings";
    }
}
