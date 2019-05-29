package com.pinwheel.anabel.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SectionDto {
    @Size(min = 4, max = 128)
    private String name;
}
