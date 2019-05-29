package com.pinwheel.anabel.controller;

import com.pinwheel.anabel.entity.Section;
import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.dto.SectionDto;
import com.pinwheel.anabel.entity.dto.UserDto;
import com.pinwheel.anabel.service.SectionService;
import com.pinwheel.anabel.service.UserService;
import com.pinwheel.anabel.service.notification.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * Injection of the {@link SectionService} bean.
     */
    private final SectionService sectionService;

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
     * Returns admin page.
     *
     * @return admin page path.
     */
    @GetMapping
    public String admin(Model model) {
        model.addAttribute("sectionsCount", sectionService.count());
        return "admin/main";
    }

    /**
     * Returns sections page.
     *
     * @param model
     * @return
     */
    @GetMapping("/sections")
    public String sections(Model model) {
        model.addAttribute("sections", sectionService.findAll());
        return "admin/sections";
    }

    /**
     * Returns sections page.
     *
     * @param model
     * @return
     */
    @PostMapping("/sections")
    public String sections(
            @Valid SectionDto sectionDto,
            BindingResult bindingResult,
            Model model) {
        NotificationMessage message;

        if (bindingResult.hasErrors()) {
            message = flushNotificationMessageFactory.create("sectionAddFailure", model);
        } else {
            Section section = modelMapper.map(sectionDto, Section.class);
            sectionService.add(section);

            message = flushNotificationMessageFactory.create("sectionAddSuccessfully", model);
        }

        notificationService.send(Notification.builder()
                .put("flush", new User(), message)
                .build());

        model.addAttribute("sections", sectionService.findAll());
        return "redirect:/admin/sections";
    }

    /**
     * Returns sections page.
     *
     * @param model
     * @return
     */
    @GetMapping("/sections/delete/{section}")
    public ResponseEntity<?> deleteSection(
            @PathVariable Section section) {
        sectionService.delete(section);
        return ResponseEntity.ok("success");
    }
}
