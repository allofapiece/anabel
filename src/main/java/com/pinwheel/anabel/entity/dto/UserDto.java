package com.pinwheel.anabel.entity.dto;

import com.pinwheel.anabel.service.validation.PasswordMatches;
import com.pinwheel.anabel.service.validation.ValidEmail;
import com.pinwheel.anabel.service.validation.ValidPassword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@PasswordMatches
public class UserDto {
    @ValidEmail()
    private String email;

    @NotNull
    @Size(min = 4, max = 20)
    private String displayName;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 8, max = 32)
    private String confirmedPassword;
}
