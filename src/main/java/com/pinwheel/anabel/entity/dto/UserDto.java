package com.pinwheel.anabel.entity.dto;

import com.pinwheel.anabel.service.validation.PasswordMatches;
import com.pinwheel.anabel.service.validation.UniqueEmail;
import com.pinwheel.anabel.service.validation.ValidEmail;
import com.pinwheel.anabel.service.validation.ValidPassword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@PasswordMatches(affectedObject = "confirmedPassword")
public class UserDto {
    @ValidEmail()
    private String email;

    @NotNull
    @Size(min = 2, max = 15)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 15)
    private String lastName;

    @NotNull
    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 4, max = 18)
    private String confirmedPassword;
}
