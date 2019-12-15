package com.pinwheel.anabel.entity;

import org.springframework.data.rest.core.config.Projection;

import java.util.Set;


@Projection(name = "default", types = {Catalog.class})
public interface CatalogProjection {
    Long getId();
    String getName();
    Status getStatus();
    User getUser();
    Set<Product> getProducts();
}
