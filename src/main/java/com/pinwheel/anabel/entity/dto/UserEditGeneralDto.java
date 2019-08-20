package com.pinwheel.anabel.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEditGeneralDto {
    @Size(min = 2, max = 32)
    @NotBlank
    private String displayName;

    @Size(min = 2, max = 16)
    private String firstName;

    @Size(min = 2, max = 16)
    private String lastName;

    @Size(min = 2, max = 32768)
    private String about;
}
