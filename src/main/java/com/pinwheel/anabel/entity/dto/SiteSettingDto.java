package com.pinwheel.anabel.entity.dto;

import com.pinwheel.anabel.entity.SiteSettingType;
import com.pinwheel.anabel.entity.Status;
import com.pinwheel.anabel.service.validation.ValidEnum;
import com.pinwheel.anabel.service.validation.ValidSiteSetting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidSiteSetting
public class SiteSettingDto {
    @Positive
    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    private String key;

    @NotNull
    @ValidEnum(allowed = {"BOOLEAN", "ARRAY_LIST", "STRING"}, enumClass = SiteSettingType.class)
    @Enumerated(EnumType.STRING)
    private SiteSettingType type;

    @NotEmpty
    @Type(type = "text")
    private String value;

    @NotNull
    @ValidEnum(allowed = {"ACTIVE", "DISABLED"}, enumClass = Status.class)
    @Enumerated(EnumType.STRING)
    private Status status;
}
