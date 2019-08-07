package com.pinwheel.anabel.service.module.asset.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Js extends IncludedAsset {
    private String src;

    public Js() {
        super();
        setName("js");
        setTag("script");
        setTagType(IncludedAssetTagType.BLOCK);
    }

    @Builder
    public Js(String template, String name, String tag, IncludedAssetTagType type, String src) {
        super(template, name, tag, type);
        this.src = src;
    }
}
