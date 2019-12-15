package com.pinwheel.anabel.entity;

import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Set;


@Projection(name = "default", types = {Product.class})
public interface ProductProjection {
    Long getId();
    String getName();
    Status getStatus();
    User getUser();
    BigDecimal getPrice();
    Set<Catalog> getCatalog();
}
