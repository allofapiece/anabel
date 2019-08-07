package com.pinwheel.anabel.service.module.asset.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncludedAsset extends Asset {
    private String tag;

    private IncludedAssetTagType tagType;

    public IncludedAsset(String template, String name, String tag, IncludedAssetTagType type) {
        super(template, name);
        this.tag = tag;
        this.tagType = type;
    }
}
