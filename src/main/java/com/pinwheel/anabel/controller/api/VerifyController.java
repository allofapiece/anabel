package com.pinwheel.anabel.controller.api;

import com.pinwheel.anabel.exception.ResourceNotFoundException;
import com.pinwheel.anabel.repository.UserRepository;
import com.pinwheel.anabel.service.SiteSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * Verify controller.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@RequestMapping("/api/action/verify")
@RestController
public class VerifyController {
    /**
     * Inject of {@link UserRepository} bean.
     */
    private final UserRepository userRepository;

    private final SiteSettingService siteSettingService;

    /**
     * Verify user slug
     *
     * @return if slug exists returns false, otherwise true.
     */
    @PostMapping("slug")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> verifySlug(@RequestBody Map<String, String> map) {
        //TODO make separate exceptions for BadRequest
        if (!map.containsKey("value")) {
            throw new ResourceNotFoundException("Request must contain `value` attribute");
        }

        String slug = map.get("value");

        // TODO verify slug full like `- /` characters and other
        return userRepository.existsBySlug(slug)
                || ((List<String>) siteSettingService.getValue("slugTakenKeywords")).contains(slug)
                ? ResponseEntity.ok().body(false)
                : ResponseEntity.ok().body(true);
    }
}
