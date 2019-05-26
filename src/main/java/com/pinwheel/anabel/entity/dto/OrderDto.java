package com.pinwheel.anabel.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class OrderDto {
    @NotEmpty
    @Size(min = 3, max = 60)
    private String title;

    @Size(max = 2048)
    private String description;

    @Max(1000000)
    private BigDecimal price;

    private Timestamp deadline;
}
