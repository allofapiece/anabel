package com.pinwheel.anabel.entity.dto;

import com.pinwheel.anabel.service.validation.PasswordMatches;
import com.pinwheel.anabel.service.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@PasswordMatches(affectedObject = "confirmedPassword")
public class UserChangePasswordDto {
    private String oldPassword;

    @ValidPassword
    private String password;

    private String confirmedPassword;
}
