package com.pinwheel.anabel.service.module.asset.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asset implements Cloneable {
    private String template;

    private String name;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
