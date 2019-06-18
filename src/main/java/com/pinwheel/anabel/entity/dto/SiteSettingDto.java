package com.pinwheel.anabel.entity.dto;

import com.pinwheel.anabel.entity.SiteSettingType;
import com.pinwheel.anabel.entity.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
public class SiteSettingDto {
    private String key;

    @Enumerated(EnumType.STRING)
    private SiteSettingType type;

    @Type(type = "text")
    private String value;

    @Enumerated(EnumType.STRING)
    private Status status;
}
