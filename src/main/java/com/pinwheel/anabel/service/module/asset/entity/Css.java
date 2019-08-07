package com.pinwheel.anabel.service.module.asset.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Css extends IncludedAsset {
    private String rel;

    private String href;

    public Css() {
        super();
        setName("css");
        setTag("link");
        setTagType(IncludedAssetTagType.BLOCK);
        setRel("stylesheet");
    }

    @Builder
    public Css(String template, String name, String tag, IncludedAssetTagType type, String rel) {
        super(template, name, tag, type);
        this.rel = rel;
    }
}
